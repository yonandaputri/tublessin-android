package com.example.final_project.domain.transaction.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.final_project.R
import com.example.final_project.domain.transaction.TransactionViewModel
import com.example.final_project.domain.transaction.recycleview.HistoryTransactionRecycleAdapter
import com.pixplicity.easyprefs.library.Prefs
import kotlinx.android.synthetic.main.fragment_history.*

class HistoryFragment : Fragment() {

    private val transactionViewModel by activityViewModels<TransactionViewModel>()
    private lateinit var userId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userId = Prefs.getString("id", "0")

        list_transaction_recycle_view.layoutManager = LinearLayoutManager(this.context)
        transactionViewModel.RequestUserTransactionList(userId)
        transactionViewModel.transactionList().observe(viewLifecycleOwner, Observer {
            if(!it.Results.results.isNullOrEmpty()) {
                list_transaction_recycle_view.adapter = HistoryTransactionRecycleAdapter(it.Results.results)
            }
        })
    }
}