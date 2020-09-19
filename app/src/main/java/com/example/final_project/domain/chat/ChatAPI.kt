package com.example.final_project.domain.chat

import com.example.final_project.domain.chat.model.ChatResponseMessage
import com.example.final_project.domain.chat.model.Conversation
import com.example.final_project.domain.chat.model.SendMessageResponse
import retrofit2.Call
import retrofit2.http.*

interface ChatAPI {
    @GET("conversation/get")
    fun GetChatList(
        @Query("senderid") senderid: String,
        @Query("receiverid") receiverid: String,
        @Header("Authorization") token: String
    ): Call<ChatResponseMessage>

    @POST("conversation/new")
    fun PostNewMessage(
        @Body conversation: Conversation,
        @Header("Authorization") token: String
    ): Call<SendMessageResponse>
}