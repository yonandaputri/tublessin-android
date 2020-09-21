package com.example.final_project.domain.transaction.recycleview

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
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
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.history_transaction_recycle_view, parent, false)
        return HistoryTransactionViewHolder(view)
    }

    override fun getItemCount(): Int {
        return transactionList.size
    }

    override fun onBindViewHolder(holder: HistoryTransactionViewHolder, position: Int) {
        holder.montirname.text = transactionList[position].montir_firstname
        if (transactionList[position].status == "Canceled") {
            holder.status.setTextColor(Color.RED)
            holder.header.setCardBackgroundColor(Color.RED)
        } else if (transactionList[position].status == "Success") {
            holder.status.setTextColor(Color.parseColor("#43C639"))
            holder.header.setCardBackgroundColor(Color.parseColor("#43C639"))
        } else {
            holder.status.setTextColor(Color.BLUE)
            holder.header.setCardBackgroundColor(Color.BLUE)
        }
        holder.status.text = transactionList[position].status
        holder.date.text = transactionList[position].date_created
        holder.transId.text = "Transaction ID: ${transactionList[position].id}"
    }
}

class HistoryTransactionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val montirname = view.findViewById<TextView>(R.id.montir_name)
    val status = view.findViewById<TextView>(R.id.status_transaction)
    val date = view.findViewById<TextView>(R.id.date_transaction)
    var header = view.findViewById<CardView>(R.id.header_color_accent_trans_recycle_view)
    var transId = view.findViewById<TextView>(R.id.trans_id_recycle_view)
}