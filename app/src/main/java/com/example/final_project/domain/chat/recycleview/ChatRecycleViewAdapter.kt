package com.example.final_project.domain.chat.recycleview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.final_project.R
import com.example.final_project.domain.chat.model.Conversation
import com.pixplicity.easyprefs.library.Prefs

class ChatRecycleViewAdapter(private val messageList: List<Conversation>) :
    RecyclerView.Adapter<ChatViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.chat_recycle_view, parent, false)
        return ChatViewHolder(view)
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val userId = Prefs.getString("id", "0")
        if (messageList[position].sender_id == userId) {
            holder.messageUser.isVisible = true
            holder.messageUser.text = messageList[position].message
            holder.imageUser.isVisible = true
            holder.senderId.text = messageList[position].sender_id
            holder.dateSender.text = messageList[position].date_created

            holder.messageMontir.isVisible = false
            holder.imageMontir.isVisible = false


        } else {
            holder.messageUser.isVisible = false
            holder.imageUser.isVisible = false

            holder.messageMontir.isVisible = true
            holder.messageMontir.text = messageList[position].message
            holder.imageMontir.isVisible = true
            holder.receiverId.text = messageList[position].sender_id
            holder.dateReceiver.text = messageList[position].date_created
        }
    }

}


class ChatViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val imageUser = view.findViewById<ImageView>(R.id.image_message_profile2)
    val imageMontir = view.findViewById<ImageView>(R.id.image_message_profile)
    val messageUser = view.findViewById<TextView>(R.id.text_message_body)
    val messageMontir = view.findViewById<TextView>(R.id.text_message_body2)
    val senderId = view.findViewById<TextView>(R.id.text_message_name)
    val receiverId = view.findViewById<TextView>(R.id.text_message_name2)
    val dateSender = view.findViewById<TextView>(R.id.text_message_time2)
    val dateReceiver = view.findViewById<TextView>(R.id.text_message_time)
}