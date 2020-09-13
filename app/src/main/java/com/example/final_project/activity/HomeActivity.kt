package com.example.final_project.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.final_project.R
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        navController = (home_nav_fragment as NavHostFragment).navController
        NavigationUI.setupWithNavController(bottom_navigation, navController)

        bottom_navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    navController.navigate(R.id.action_to_maps)
                    this.fragmentManager.popBackStack()
                    true
                }

                R.id.chat -> {
                    navController.navigate(R.id.action_to_chat)
                    this.fragmentManager.popBackStack()
                    true
                }
                R.id.history -> {
                    navController.navigate(R.id.action_to_history)
                    this.fragmentManager.popBackStack()
                    true
                }
                R.id.profile -> {
                    navController.navigate(R.id.action_to_profile)
                    this.fragmentManager.popBackStack()
                    true
                }
                else -> {
                    println("MASUK ELSE")
                    false
                }
            }
        }

    }
}