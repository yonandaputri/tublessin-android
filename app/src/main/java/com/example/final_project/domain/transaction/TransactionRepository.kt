package com.example.final_project.domain.transaction

import androidx.lifecycle.MutableLiveData
import com.example.final_project.domain.transaction.model.Transaction
import com.example.final_project.domain.transaction.model.TransactionResponeMessage
import com.pixplicity.easyprefs.library.Prefs
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class TransactionRepository(val transactionAPI: TransactionAPI) {
    val transactionLiveData = MutableLiveData<TransactionResponeMessage>()
    val token = "Bearer ${Prefs.getString("token", "0")}"

    fun UpdateStatusTransaction(transactionId: String) {
        transactionAPI.UpdateStatusTransaction(transactionId, token)
            .enqueue(object : Callback<TransactionResponeMessage> {
                override fun onResponse(
                    call: Call<TransactionResponeMessage>,
                    response: Response<TransactionResponeMessage>
                ) {
                    if (response.code() == 200) {
                        println(response.code())
                        println(response.body())
                        transactionLiveData.value = response.body()
                    }
                    println(response.code())
                    println(response.body())
                }

                override fun onFailure(call: Call<TransactionResponeMessage>, t: Throwable) {
                    t.printStackTrace()
                }
            })
    }

    fun GetUserTransactionList(userid: String) {
        transactionAPI.GetUserTransactionList(userid, "", token)
            .enqueue(object : Callback<TransactionResponeMessage> {
                override fun onResponse(
                    call: Call<TransactionResponeMessage>,
                    response: Response<TransactionResponeMessage>
                ) {
                    if (response.code() == 200) {
                        println(response.code())
                        println(response.body())
                        transactionLiveData.value = response.body()
                    }
                    println(response.code())
                    println(response.body())
                }

                override fun onFailure(call: Call<TransactionResponeMessage>, t: Throwable) {
                    t.printStackTrace()
                }
            })
    }

    fun PostNewTransaction(transaction: Transaction) {
        transactionAPI.PostNewTransaction(transaction, token)
            .enqueue(object : Callback<TransactionResponeMessage> {
                override fun onResponse(
                    call: Call<TransactionResponeMessage>,
                    response: Response<TransactionResponeMessage>
                ) {
                    if (response.code() == 200) {
                        println(response.code())
                        println(response.body())
                        transactionLiveData.value = response.body()
                    }
                    println(response.code())
                    println(response.body())
                }

                override fun onFailure(call: Call<TransactionResponeMessage>, t: Throwable) {
                    t.printStackTrace()
                }
            })
    }
}