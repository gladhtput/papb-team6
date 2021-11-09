package com.dteti.animapp.services.persistenceservice

import com.dteti.animapp.services.persistenceservice.room.entities.Anime

interface AnimePersistenceService {
    suspend fun readAllAnimes(): List<Anime>
    suspend fun readAnimeDetailsById(id: Int): Anime?
    suspend fun persistAnime(anime: Anime)
    suspend fun deleteAnime(anime: Anime)
}