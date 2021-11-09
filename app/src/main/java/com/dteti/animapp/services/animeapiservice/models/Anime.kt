package com.dteti.animapp.services.animeapiservice.models

import com.google.gson.annotations.SerializedName

data class Anime (
    @SerializedName("mal_id") val id: String,
    @SerializedName("image_url") val mainPictureUrl: String,
    val score: String,
    val title: String,
    val synopsis: String
)