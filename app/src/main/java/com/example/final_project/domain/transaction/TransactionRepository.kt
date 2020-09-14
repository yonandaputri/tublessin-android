package com.example.final_project.domain.transaction

import androidx.lifecycle.MutableLiveData
import com.example.final_project.domain.transaction.model.Transaction
import com.example.final_project.domain.transaction.model.TransactionResponeMessage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class TransactionRepository(val transactionAPI: TransactionAPI) {
    val transactionLiveData = MutableLiveData<TransactionResponeMessage>()

    fun UpdateStatusTransaction(transactionId:String){
        transactionAPI.UpdateStatusTransaction(transactionId).enqueue(object:Callback<TransactionResponeMessage>{
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
                println(response.body())            }

            override fun onFailure(call: Call<TransactionResponeMessage>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    fun GetUserTransactionList(userid:String){
        transactionAPI.GetUserTransactionList(userid).enqueue(object:Callback<TransactionResponeMessage>{
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
                println(response.body())            }

            override fun onFailure(call: Call<TransactionResponeMessage>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    fun PostNewTransaction(transaction:Transaction){
        transactionAPI.PostNewTransaction(transaction).enqueue(object:Callback<TransactionResponeMessage>{
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