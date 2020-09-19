package com.example.final_project.domain.chat

import androidx.lifecycle.MutableLiveData
import com.example.final_project.domain.chat.model.ChatResponseMessage
import com.example.final_project.domain.chat.model.Conversation
import com.example.final_project.domain.chat.model.SendMessageResponse
import com.pixplicity.easyprefs.library.Prefs
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChatRepository(val chatAPI: ChatAPI) {
    var chatLiveData = MutableLiveData<ChatResponseMessage>()
    var sendMessageLiveData = MutableLiveData<SendMessageResponse>()
    val token = "Bearer ${Prefs.getString("token", "0")}"

    fun GetChatList(senderid: String, receiverid: String) {
        chatAPI.GetChatList(senderid, receiverid, token)
            .enqueue(object : Callback<ChatResponseMessage> {
                override fun onFailure(call: Call<ChatResponseMessage>, t: Throwable) {
                    t.printStackTrace()
                }

                override fun onResponse(
                    call: Call<ChatResponseMessage>,
                    response: Response<ChatResponseMessage>
                ) {
                    if (response.code() == 200) {
                        println(response.code())
                        println(response.body())
                        chatLiveData.value = response.body()
                    }
                    println(response.code())
                    println(response.body())
                }

            })
    }

    fun PostNewMessage(conversation: Conversation) {
        chatAPI.PostNewMessage(conversation, token).enqueue(object : Callback<SendMessageResponse> {
            override fun onFailure(call: Call<SendMessageResponse>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(
                call: Call<SendMessageResponse>,
                response: Response<SendMessageResponse>
            ) {
                if (response.code() == 200) {
                    println(response.code())
                    println(response.body())
                    sendMessageLiveData.value = response.body()
                }
                println(response.code())
                println(response.body())
            }

        })
    }
}