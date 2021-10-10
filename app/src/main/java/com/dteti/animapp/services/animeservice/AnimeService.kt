package com.dteti.animapp.services.animeservice

interface AnimeService {
    suspend fun getAnimeByIdAsync(id: Int): Anime?
    suspend fun searchAnime(
        keywords: String,
        page: Int
    ): List<Anime>
}