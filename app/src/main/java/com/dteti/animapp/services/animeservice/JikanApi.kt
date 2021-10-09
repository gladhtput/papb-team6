package com.dteti.animapp.services.animeservice

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface JikanApi {
    @GET("anime/{id}")
    suspend fun getAnimeByIdAsync(@Path("id") id: Int): Response<Anime>
}