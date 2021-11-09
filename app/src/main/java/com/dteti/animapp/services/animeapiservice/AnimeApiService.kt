package com.dteti.animapp.services.animeapiservice

import com.dteti.animapp.services.animeapiservice.models.Anime
import com.dteti.animapp.services.animeapiservice.models.AnimeDetails

interface AnimeApiService {
    suspend fun getAnimeDetailsById(id: Int): AnimeDetails?
    suspend fun searchAnime(
        keywords: String,
        page: Int
    ): List<Anime>
    suspend fun isAvailable(): Boolean
}