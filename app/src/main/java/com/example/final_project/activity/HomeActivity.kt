package com.example.final_project.activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.AttributeSet
import android.view.View
import androidx.core.view.isVisible
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
import kotlinx.android.synthetic.main.fragment_montir_detail.*

class HomeActivity : AppCompatActivity() {
    lateinit var navController: NavController
    private val transactionViewModel = TransactionViewModel()
    private lateinit var userId: String
    private lateinit var montirId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        userId = Prefs.getString("id", "0")
        montirId = Prefs.getString("montirId", "0")

        navController = (home_nav_fragment as NavHostFragment).navController
        NavigationUI.setupWithNavController(bottom_navigation, navController)

        bottom_navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    navController.navigate(R.id.action_to_maps) // masuk maps
                    true
                }
                R.id.history -> {
                    navController.navigate(R.id.action_to_history) // masuk history
                    true
                }
                R.id.profile -> {
                    navController.navigate(R.id.action_to_profile) // masuk profile
                    true
                }
                else -> {
                    println("MASUK ELSE")
                    false
                }
            }
        }

        transactionViewModel.transactionList().observe(this, Observer {
            if (!it.Results.results.isNullOrEmpty()) {
                println(it.Results.results[0].status)
                // di cek jika status orderan nya bukan "On Process" alias sudah selesai dan
                // statusOrderan nya true yang artinya sebelumnya lagi sedang ada orderan
                // Prefs.getBoolean("statusOrderan", false) <- dilakukan biar pas awal login ngga langsung
                // munculin fragment review
                if (it.Results.results[0].status == "Success" && Prefs.getBoolean(
                        "statusOrderan",
                        false
                    )
                ) {
                    // Orderan Selesai
                    Prefs.putBoolean("statusOrderan", false) // ubah status orderan jadi false
                    Prefs.putString("finishedOrderMontirId", it.Results.results[0].id_montir)
                    navController.navigate(R.id.action_global_giveMontirRatingFragment) // pindah ke fragment rating
                } else if (it.Results.results[0].status == "On Process") {
                    // Ada orderan yang sedang berjalan
                    Prefs.putBoolean("statusOrderan", true) // status orderan true
                } else if (it.Results.results[0].status == "Canceled" && Prefs.getBoolean(
                        "statusOrderan",
                        false
                    )
                ) {
                    // Orderan Selesai
                    Prefs.putBoolean("statusOrderan", false) // ubah status orderan jadi false
                    Prefs.putString("finishedOrderMontirId", it.Results.results[0].id_montir)
                    order_button_montir_detail.isVisible = true
                    cancel_button_montir_detail.isVisible = false
                    chat_button_montir_detail.isVisible = false
                    navController.navigate(R.id.action_to_maps)
//                    Navigation.findNavController(view)
//                        .navigate(R.id.action_montirDetailFragment_pop)
//                    Navigation.findNavController(view).navigate(R.id.action_to_maps)

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