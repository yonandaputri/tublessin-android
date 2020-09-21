package com.example.final_project.domain.transaction

import com.example.final_project.domain.transaction.model.Transaction
import com.example.final_project.domain.transaction.model.TransactionResponeMessage
import retrofit2.Call
import retrofit2.http.*

interface TransactionAPI {
    @POST("transaction/add")
    fun PostNewTransaction(
        @Body transaction: Transaction, @Header("Authorization") token: String
    ): Call<TransactionResponeMessage>

    @GET("transaction/history/get")
    fun GetUserTransactionList(
        @Query("userid") userid: String,
        @Query("montirid") montirid: String,
        @Header("Authorization") token: String

    ): Call<TransactionResponeMessage>

    @POST("transaction/update/status/{id}")
    fun UpdateStatusTransaction(
        @Body transaction: Transaction,
        @Path("id") id: String,
        @Header("Authorization") token: String
    ): Call<TransactionResponeMessage>
}