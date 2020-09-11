package com.example.final_project.domain.login

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginAPI {
    @POST("account/login/user")
    fun requestUserLogin(@Body accountInfo: AccountInfo): Call<Login>
}