package com.example.final_project.domain.montir

import androidx.lifecycle.MutableLiveData
import com.example.final_project.domain.montir.model.MontirResponeMessage
import com.example.final_project.domain.montir.model.NearbyMontirResponeMessage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MontirRepository(val montirAPI: MontirAPI) {
    var nearbyMontir = MutableLiveData<NearbyMontirResponeMessage>()
    var montirDetail = MutableLiveData<MontirResponeMessage>()

    fun getMontirDetailById(id:String){
        montirAPI.getMontirDetailById(id).enqueue(object :Callback<MontirResponeMessage>{
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
        montirAPI.findNearbyMontir(lat, long)
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