package com.dteti.animapp.services.animeservice

import android.util.Log
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.SocketTimeoutException
import kotlin.random.Random

class JikanAnimeService : AnimeService {
    private val jikanApi = Retrofit.Builder()
        .baseUrl("https://api.jikan.moe/v3/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(JikanApi::class.java)

    override suspend fun getAnimeByIdAsync(id: Int): Anime? {
        val response = jikanApi.getAnimeByIdAsync(id)

        return response.body()
    }

    override suspend fun getRandomAnime(): Anime? {
        do {
            try {
                val id = Random.nextInt(0, 17100)

                Log.i("JikanAnimeService", "Fetching anime with the ID $id.")

                val anime = jikanApi.getAnimeByIdAsync(id).body()
                if (anime != null && isSafeForWork(anime)) {
                    return anime
                } else if (anime == null) {
                    return null
                }
            } catch (err: SocketTimeoutException) {
                Log.i("Jikan", "Anime fetching timed out. Retrying...")
            }
        } while(true)
    }

    private fun isSafeForWork(anime: Anime): Boolean {
        val bannedRatings = setOf("Rx - Hentai")

        return bannedRatings.contains(anime.rating)
    }
}