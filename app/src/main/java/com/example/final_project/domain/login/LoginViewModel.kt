package com.example.final_project.domain.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.final_project.config.RetrofitBuilder

class LoginViewModel : ViewModel() {
    val loginRepository: LoginRepository

    init {
        val loginAPI = RetrofitBuilder.createRetrofit().create(LoginAPI::class.java)
        loginRepository = LoginRepository(loginAPI)
    }

    fun getLoginAccountInfo() = loginRepository.loginAccountInfo as LiveData<Login>

    fun requestUserLogin(accountInfo: AccountInfo) {
        loginRepository.requestUserLogin(accountInfo)
    }
}