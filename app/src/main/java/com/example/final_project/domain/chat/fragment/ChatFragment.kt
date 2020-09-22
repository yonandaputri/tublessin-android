package com.example.final_project.domain.chat.fragment

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.final_project.R
import com.example.final_project.domain.chat.ChatViewModel
import com.example.final_project.domain.chat.model.Conversation
import com.example.final_project.domain.chat.recycleview.ChatRecycleViewAdapter
import com.pixplicity.easyprefs.library.Prefs
import kotlinx.android.synthetic.main.fragment_chat.*

class ChatFragment : Fragment(), View.OnClickListener {

    private val chatViewModel = ChatViewModel()
    private lateinit var userId: String
    private lateinit var montirId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button_chatbox_send.setOnClickListener(this)

        userId = Prefs.getString("id", "0")
        montirId = Prefs.getString("selectedMontirId", "0")
        println("userId pas chat : ${userId}")
        println("montirId pas chat : ${montirId}")

        reyclerview_chat.layoutManager = LinearLayoutManager(this.context)
        chatViewModel.RequestChatList(userId, montirId)

        chatViewModel.getChatDetail().observe(viewLifecycleOwner, Observer {
            if (!it.Results.conversation.isNullOrEmpty()) {
                reyclerview_chat.adapter = ChatRecycleViewAdapter(it.Results.conversation.reversed())
            }
        })
    }

    override fun onResume() {
        super.onResume()
        val handler = Handler()
        val runnable: Runnable = object : Runnable {
            override fun run() {
                chatViewModel.RequestChatList(userId, montirId)
                handler.postDelayed(this, 2000)
            }
        }
    }

    override fun onClick(v: View?) {
        when (v) {
            button_chatbox_send -> {
                chatViewModel.PostNewMessage(Conversation(
                    sender_id = userId,
                    receiver_id = montirId,
                    message = edittext_chatbox.text.toString()
                ))
            }
        }
    }
}