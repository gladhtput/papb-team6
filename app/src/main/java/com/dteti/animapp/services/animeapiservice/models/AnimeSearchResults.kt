package com.dteti.animapp.services.animeapiservice.models

import com.google.gson.annotations.SerializedName

data class AnimeSearchResults (
    val results: List<Anime>,
    @SerializedName("last_page") val lastPage: Int
)