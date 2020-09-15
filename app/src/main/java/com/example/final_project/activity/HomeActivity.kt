package com.example.final_project.activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.AttributeSet
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.final_project.R
import com.example.final_project.domain.transaction.TransactionViewModel
import com.pixplicity.easyprefs.library.Prefs
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.fragment_maps.*

class HomeActivity : AppCompatActivity() {
    lateinit var navController: NavController
    private val transactionViewModel = TransactionViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        navController = (home_nav_fragment as NavHostFragment).navController
        NavigationUI.setupWithNavController(bottom_navigation, navController)

        bottom_navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    navController.navigate(R.id.action_to_maps)
//                    this.fragmentManager.popBackStack()
                    true
                }
                R.id.history -> {
                    navController.navigate(R.id.action_to_history)
//                    this.fragmentManager.popBackStack()
                    true
                }
                R.id.profile -> {
                    navController.navigate(R.id.action_to_profile)
//                    this.fragmentManager.popBackStack()
                    true
                }
                else -> {
                    println("MASUK ELSE")
                    false
                }
            }
        }

        transactionViewModel.transactionList().observe(this, Observer {
            if(!it.Results.results.isNullOrEmpty()){
                println(it.Results.results[0].status)
                if (it.Results.results[0].status != "On Process"){
                    findNearbyButton.isEnabled = true
                    println("Orderan sudah selesai")
                }else{
                    println("Orderan sedang berjalan")
                    findNearbyButton.isEnabled = false
                }
            }
        })
    }



    override fun onResume() {
        super.onResume()
        val handler = Handler()
        val runnable: Runnable = object : Runnable {
            override fun run() {
                transactionViewModel.RequestUserTransactionList(Prefs.getString("id", "0"))
                handler.postDelayed(this, 3000)
            }
        }
        handler.postDelayed(runnable, 200)
    }
}