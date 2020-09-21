package com.example.final_project.domain.transaction

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.final_project.config.RetrofitBuilder
import com.example.final_project.domain.transaction.model.Transaction
import com.example.final_project.domain.transaction.model.TransactionResponeMessage

class TransactionViewModel : ViewModel() {
    val transactionRepository = TransactionRepository(
        RetrofitBuilder.createRetrofit().create(
            TransactionAPI::class.java
        )
    )

    fun transactionList() =
        transactionRepository.transactionLiveData as LiveData<TransactionResponeMessage>

    fun RequestUserTransactionList(userid: String) =
        transactionRepository.GetUserTransactionList(userid)

    fun UpdateStatusTransaction(statusTransaction: Transaction, transactionId: String) =
        transactionRepository.UpdateStatusTransaction(statusTransaction, transactionId)

    fun PostNewTransaction(transaction: Transaction) =
        transactionRepository.PostNewTransaction(transaction)
}