package com.dteti.animapp.services.animeservice

interface AnimeService {
    suspend fun getAnimeDetailsById(id: Int): AnimeDetails?
    suspend fun searchAnime(
        keywords: String,
        page: Int
    ): List<Anime>
}