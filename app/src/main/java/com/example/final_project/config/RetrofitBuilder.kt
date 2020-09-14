package com.example.final_project.config

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


fun defaultHost():String{
    return "http://34.101.198.49:8084/"
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