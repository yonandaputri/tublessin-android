package com.example.final_project.domain.montir.model


class NearbyMontirResponeMessage(
    val response: String = "",
    val code: String = "",
    val total_montir: String = "",
    val list : List<NearbyMontirProfile>
)

class NearbyMontirProfile(
    val id: Int = 0,
    val firstname: String = "",
    val lastname: String = "",
    val imageUrl: String = "",
    val distance: String = "",
    val rating: NearbyMontirRating,
    val location: NearbyMontirLocation,
    val status: NearbyMontirStatus
)

class NearbyMontirRating(
    val total_rating: String = "",
    val average_rating: String = ""
)

class NearbyMontirLocation(
    val latitude: String = "",
    val longitude: String = "",
    val date_updated: String = ""
)

class NearbyMontirStatus(
    val status_operational: String = "",
    val status_activity: String = ""
)