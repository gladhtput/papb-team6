package com.dteti.animapp.usecases

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.dteti.animapp.common.exceptions.NoAnimeServiceException
import com.dteti.animapp.dto.AnimeDetails
import com.dteti.animapp.dto.AnimeSummary
import com.dteti.animapp.dto.UriType
import com.dteti.animapp.services.animeapiservice.AnimeApiService
import com.dteti.animapp.services.persistenceservice.AnimePersistenceService
import com.dteti.animapp.services.persistenceservice.room.entities.Anime
import java.lang.Exception
import javax.inject.Inject

class AnimeUseCases @Inject constructor(
    private val apiService: AnimeApiService,
    private val persistenceService: AnimePersistenceService) {

    suspend fun searchAnime(keywords: String, page: Int): List<AnimeSummary> {
        try {
            val animes = apiService.searchAnime(keywords, page)

            return animes.map {
                AnimeSummary(
                    it.id.toInt(),
                    it.mainPictureUrl,
                    UriType.URL,
                    it.score,
                    it.title,
                    it.synopsis
                )
            }
        } catch(err: Exception) {
            throw NoAnimeServiceException()
        }
    }

    suspend fun readAllFavoriteAnimes(): List<AnimeSummary> {
        val animes = persistenceService.readAllAnimes()

        return animes.map {
            AnimeSummary(
                it.id,
                it.mainPicturePath,
                UriType.PATH,
                it.score,
                it.title,
                it.synopsis
            )
        }
    }

    suspend fun readAnimeById(animeId: Int): AnimeDetails? {
        val persistedAnime = persistenceService.readAnimeDetailsById(animeId)

        if (apiService.isAvailable()) {
            val anime = apiService.getAnimeDetailsById(animeId)

            return if (anime != null) {
                AnimeDetails(
                    anime.id.toInt(),
                    anime.mainPictureUrl,
                    UriType.URL,
                    anime.title,
                    anime.score,
                    anime.ageRating,
                    anime.episodeCount,
                    anime.airingStatus,
                    anime.synopsis,
                    persistedAnime != null
                )
            } else {
                null
            }
        } else {
            return if (persistedAnime != null) {
                AnimeDetails(
                    persistedAnime.id,
                    persistedAnime.mainPicturePath,
                    UriType.PATH,
                    persistedAnime.title,
                    persistedAnime.score,
                    persistedAnime.ageRating,
                    persistedAnime.episodeCount,
                    persistedAnime.airingStatus,
                    persistedAnime.synopsis,
                    true
                )
            } else {
                null
            }
        }
    }

    suspend fun setFavorite(animeId: Int, isFavorite: Boolean) {
        val persistedAnime = persistenceService.readAnimeDetailsById(animeId)

        if (isFavorite && persistedAnime == null) {
            val fetchedAnime = apiService.getAnimeDetailsById(animeId)

            if (fetchedAnime != null) {
                persistenceService.persistAnime(Anime(
                    fetchedAnime.id.toInt(),
                    fetchedAnime.mainPictureUrl,
                    fetchedAnime.title,
                    fetchedAnime.score,
                    fetchedAnime.ageRating,
                    fetchedAnime.episodeCount,
                    fetchedAnime.airingStatus,
                    fetchedAnime.synopsis
                ))
            }
        } else if (!isFavorite && persistedAnime != null) {
            persistenceService.deleteAnime(persistedAnime)
        }
    }
}