package com.dteti.animapp.services.animeapiservice.models

import com.google.gson.annotations.SerializedName

data class AnimeDetails(
    @SerializedName("mal_id") val id: String,
    @SerializedName("image_url") val mainPictureUrl: String,
    val title: String,
    val score: String,
    @SerializedName("rating") val ageRating: String,
    @SerializedName("episodes") val episodeCount: Int,
    @SerializedName("status") val airingStatus: String,
    val synopsis: String
)