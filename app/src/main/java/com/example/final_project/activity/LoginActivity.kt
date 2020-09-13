package com.example.final_project.activity

import android.content.ContextWrapper
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.final_project.R
import com.example.final_project.domain.login.AccountInfo
import com.example.final_project.domain.login.LoginViewModel
import com.pixplicity.easyprefs.library.Prefs
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private val loginViewModel = LoginViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        Prefs.Builder().setContext(this).setMode(ContextWrapper.MODE_PRIVATE)
            .setPrefsName(packageName).setUseDefaultSharedPreference(true).build()
    }

    fun onLoginClick(view: View) {
        if (editTextUsername.text.toString() == "" || editTextPassword.text.toString() == "") {
            Toast.makeText(
                this,
                "Username atau Password Kosong",
                Toast.LENGTH_LONG
            ).show()
        } else {
            loginViewModel.requestUserLogin(
                AccountInfo(
                    username = editTextUsername.text.toString(),
                    password = editTextPassword.text.toString()
                )
            )
            loginViewModel.getLoginAccountInfo().observe(this, Observer {
                if (it != null) {
                    println("succes login")

                    Prefs.putString("id", it.account.id.toString())
                    startActivity(Intent(this, HomeActivity::class.java))
                }
            })
        }
    }

    fun onRegisterClick(view: View) {
        startActivity(Intent(this, RegisterActivity::class.java))
    }

    fun onForgotPassword(view: View) {
        startActivity(Intent(this, ForgotPasswordActivity::class.java))
    }
}
