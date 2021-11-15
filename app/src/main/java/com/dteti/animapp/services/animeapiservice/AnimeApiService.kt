package com.dteti.animapp.services.animeapiservice

import com.dteti.animapp.services.animeapiservice.models.Anime
import com.dteti.animapp.services.animeapiservice.models.AnimeDetails
import com.dteti.animapp.services.animeapiservice.models.AnimeSearchResults

interface AnimeApiService {
    suspend fun getAnimeDetailsById(id: Int): AnimeDetails?
    suspend fun searchAnime(
        keywords: String,
        page: Int
    ): AnimeSearchResults?
    fun isAvailable(): Boolean
}