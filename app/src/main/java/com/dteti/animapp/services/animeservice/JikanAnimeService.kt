package com.dteti.animapp.services.animeservice

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class JikanAnimeService : AnimeService {
    private val jikanApi = Retrofit.Builder()
        .baseUrl("https://api.jikan.moe/v3/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(JikanApi::class.java)

    override suspend fun getAnimeDetailsById(id: Int): AnimeDetails? {
        val response = jikanApi.getAnimeDetailsById(id)

        return response.body()
    }

    override suspend fun searchAnime(keywords: String, page: Int): List<Anime> {
        val response = jikanApi.searchAnime(keywords, page)
        val body = response.body()

        return body?.results ?: emptyList()
    }
}