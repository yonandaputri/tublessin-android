package com.example.final_project.domain.montir

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.final_project.config.RetrofitBuilder
import com.example.final_project.domain.montir.model.MontirResponeMessage
import com.example.final_project.domain.montir.model.NearbyMontirResponeMessage


class MontirViewModel : ViewModel() {
    val montirRepository =
        MontirRepository(RetrofitBuilder.createRetrofit().create(MontirAPI::class.java))

    fun getNearbyMontir() =
        montirRepository.nearbyMontir as LiveData<NearbyMontirResponeMessage>

    fun requestNearbyMontir(lat: String, long: String) =
        montirRepository.findNearbyMontir(lat, long)

    fun getMontirDetail() = montirRepository.montirDetail as LiveData<MontirResponeMessage>
    fun requestMontirDetailById(id: String) = montirRepository.getMontirDetailById(id)
}