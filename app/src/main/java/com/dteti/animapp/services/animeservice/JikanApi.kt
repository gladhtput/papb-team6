package com.dteti.animapp.services.animeservice

import com.dteti.animapp.services.animeservice.Anime
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface JikanApi {
    @GET("anime/{id}")
    suspend fun getAnimeById(@Path("id") id: Int): Response<Anime>

    @GET("search/anime")
    suspend fun searchAnime(
        @Query("q") keywords: String,
        @Query("page") page: Int = 1,
        @Query("genre") genre: Int = 12,
        @Query("genre_exclude") genreExclude: Int = 1,
        @Query("limit") limit: Int = 10
    ) : Response<AnimeSearchResult>
}