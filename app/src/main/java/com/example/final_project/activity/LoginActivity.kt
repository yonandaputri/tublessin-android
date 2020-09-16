package com.example.final_project.activity

import android.content.ContextWrapper
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.final_project.R
import com.example.final_project.domain.login.AccountInfo
import com.example.final_project.domain.login.LoginViewModel
import com.example.final_project.domain.user.UserAccount
import com.example.final_project.domain.user.UserLocation
import com.example.final_project.domain.user.UserProfile
import com.example.final_project.domain.user.UserViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.pixplicity.easyprefs.library.Prefs
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {
    // Untuk keperluan Oauth Google
    private val RC_SIGN_IN = 12345
    lateinit var mGoogleSignInClient: GoogleSignInClient
    private val userViewModel by viewModels<UserViewModel>()
    private val loginViewModel = LoginViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        Prefs.Builder().setContext(this).setMode(ContextWrapper.MODE_PRIVATE)
            .setPrefsName(packageName).setUseDefaultSharedPreference(true).build()

        if (Prefs.getString("id", "0") != "0" &&
            Prefs.getString("token", "0") != "0"
        ) {
            startActivity(Intent(this, HomeActivity::class.java))
        }

        InitializeGoogleOauth() // function nya dibawah
        checkStatusLogin()
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
                    Prefs.putString("token", it.token)
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

    // Mulai dari sini sampai loginUsingGoogleAccount adalah keperluan Google Auth
    private fun checkStatusLogin() {
        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        val account = GoogleSignIn.getLastSignedInAccount(this)
        println("============================")
        println(account)
        println("============================")
        if (account != null) {
            println("masuk ke logout")
            mGoogleSignInClient.signOut()
        }
    }

    private fun InitializeGoogleOauth() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        google_logo_login_button.setOnClickListener {
            signIn()
        }
    }

    private fun signIn() {
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            // sukses Login
            val personId = registerUserGoogleAccountToDatabase()
            loginUsingGoogleAccount(personId)
        } catch (e: ApiException) {
            Log.w("Error", "signInResult:failed code=" + e.statusCode)
        }
    }

    private fun registerUserGoogleAccountToDatabase(): String? {
        // buat dapetin data user dari google
        val acct = GoogleSignIn.getLastSignedInAccount(this)
        var personId: String? = ""
        if (acct != null) {
            val personName = acct.displayName
            val personEmail = acct.email
            val personGivenName = acct.givenName
            val personFamilyName = acct.familyName
            personId = acct.id

            println("============================================")
            println(personId)
            println("============================================")
            userViewModel.registerUser(
                UserAccount(
                    username = "google-${personId}",
                    password = "google-${personId}",
                    profile = UserProfile(
                        firstname = personGivenName ?: "Belum Terdaftar",
                        lastname = personFamilyName ?: "Belum Terdaftar",
                        gender = "L",
                        phone_number = "Belum Terdaftar",
                        email = personEmail ?: "Belum Terdaftar",
                        location = UserLocation()
                    )
                )
            )
        }
        return personId
    }

    private fun loginUsingGoogleAccount(personId: String?) {
        userViewModel.getUserAccountInfo().observe(this, Observer {
            loginViewModel.requestUserLogin(
                AccountInfo(
                    username = "google-${personId}",
                    password = "google-${personId}"
                )
            )
        })

        loginViewModel.getLoginAccountInfo().observe(this, Observer {
            if (it != null) {
                println("succes login")
                Prefs.putString("id", it.account.id.toString())
                Prefs.putString("token", it.token)
                startActivity(Intent(this, HomeActivity::class.java))
            }
        })
    }
}
