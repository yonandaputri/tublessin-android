package com.example.final_project.domain.transaction.recycleview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.final_project.R
import com.example.final_project.domain.transaction.fragment.HistoryFragment
import com.example.final_project.domain.transaction.model.Transaction

class HistoryTransactionRecycleAdapter(
    private val transactionList: List<Transaction>
) : RecyclerView.Adapter<HistoryTransactionViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HistoryTransactionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.history_transaction_recycle_view, parent, false)
        return HistoryTransactionViewHolder(view)
    }

    override fun getItemCount(): Int {
        return transactionList.size
    }

    override fun onBindViewHolder(holder: HistoryTransactionViewHolder, position: Int) {
        holder.montirname.text = transactionList[position].montir_firstname
        holder.status.text = transactionList[position].status
        holder.date.text = transactionList[position].date_created
    }

}

class HistoryTransactionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val montirname = view.findViewById<TextView>(R.id.montir_name)
    val status = view.findViewById<TextView>(R.id.status_transaction)
    val date = view.findViewById<TextView>(R.id.date_transaction)
}