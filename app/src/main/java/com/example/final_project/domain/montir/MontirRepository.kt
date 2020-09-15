package com.example.final_project.domain.montir

import androidx.lifecycle.MutableLiveData
import com.example.final_project.domain.montir.model.MontirRating
import com.example.final_project.domain.montir.model.MontirResponeMessage
import com.example.final_project.domain.montir.model.NearbyMontirResponeMessage
import com.pixplicity.easyprefs.library.Prefs
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MontirRepository(val montirAPI: MontirAPI) {
    var nearbyMontir = MutableLiveData<NearbyMontirResponeMessage>()
    var montirDetail = MutableLiveData<MontirResponeMessage>()
    val token = "Bearer ${Prefs.getString("token", "0")}"

    fun postNewMontirRating(id: String, montirRating: MontirRating) {
        montirAPI.postNewMontirRating(token, id, montirRating)
            .enqueue(object : Callback<MontirResponeMessage> {
                override fun onResponse(
                    call: Call<MontirResponeMessage>,
                    response: Response<MontirResponeMessage>
                ) {
                    if (response.code() == 200) {
                        println("POST RATING BY ID SUCCESS")
                        println(response.code())
                        println(response.body())
                        montirDetail.value = response.body()
                    }
                    println(response.code())
                    println(response.body())
                }

                override fun onFailure(call: Call<MontirResponeMessage>, t: Throwable) {
                    println("POST RATING BY ID FAILED")
                    t.printStackTrace()
                }
            })
    }

    fun getMontirDetailById(id: String) {
        montirAPI.getMontirDetailById(token, id).enqueue(object : Callback<MontirResponeMessage> {
            override fun onResponse(
                call: Call<MontirResponeMessage>,
                response: Response<MontirResponeMessage>
            ) {
                if (response.code() == 200) {
                    println("GET MONTIR PROFILE BY ID SUCCESS")
                    println(response.code())
                    println(response.body())
                    montirDetail.value = response.body()
                }
                println(response.code())
                println(response.body())
            }

            override fun onFailure(call: Call<MontirResponeMessage>, t: Throwable) {
                println("GET MONTIR PROFILE BY ID FAILED")
                t.printStackTrace()
            }
        })
    }

    fun findNearbyMontir(lat: String, long: String) {
        montirAPI.findNearbyMontir(token, lat, long)
            .enqueue(object : Callback<NearbyMontirResponeMessage> {
                override fun onResponse(
                    call: Call<NearbyMontirResponeMessage>,
                    response: Response<NearbyMontirResponeMessage>
                ) {
                    if (response.code() == 200) {
                        println("FIND NEARBY MONTIR SUCCESS")
                        println(response.code())
                        println(response.body())
                        nearbyMontir.value = response.body()
                    }
                    println(response.code())
                    println(response.body())
                }

                override fun onFailure(call: Call<NearbyMontirResponeMessage>, t: Throwable) {
                    println("FIND NEARBY MONTIR FAILED")
                    t.printStackTrace()
                }
            })
    }
}