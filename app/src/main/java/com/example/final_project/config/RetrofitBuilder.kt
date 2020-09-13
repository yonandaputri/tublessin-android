package com.example.final_project.config

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


fun defaultHost():String{
    return "http://192.168.43.155:8080/"
}


class RetrofitBuilder {
    companion object {
        val BASE_URL = defaultHost()

        fun createRetrofit(): Retrofit {
            val retrofit = Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build()
            return retrofit
        }
    }
}