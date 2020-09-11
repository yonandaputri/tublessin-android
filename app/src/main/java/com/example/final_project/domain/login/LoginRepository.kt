package com.example.final_project.domain.login

import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginRepository(val loginAPI: LoginAPI) {
    var loginAccountInfo = MutableLiveData<Login>()

    fun requestUserLogin(accountInfo: AccountInfo) {
        loginAPI.requestUserLogin(accountInfo).enqueue(object : Callback<Login> {
            override fun onFailure(call: Call<Login>, t: Throwable) {
                println("LOGIN FAILED")
                t.printStackTrace()
            }

            override fun onResponse(call: Call<Login>, response: Response<Login>) {
                if (response.code() == 200) {
                    println("LOGIN SUCCESS")
                    println(response.code())
                    println(response.body())
                    loginAccountInfo.value = response.body()
                } else {
                    println("LOGIN FAILED")
                    println(response.code())
                    println(response.body())
                    loginAccountInfo.value = response.body()
                }
            }

        })
    }
}