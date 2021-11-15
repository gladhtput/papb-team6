package com.dteti.animapp.services.animeapiservice

import com.dteti.animapp.services.animeapiservice.models.Anime
import com.dteti.animapp.services.animeapiservice.models.AnimeDetails
import com.dteti.animapp.services.animeapiservice.models.AnimeSearchResults
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface JikanApi {
    @GET("anime/{id}")
    suspend fun getAnimeDetailsById(@Path("id") id: Int): Response<AnimeDetails>

    @GET("search/anime")
    suspend fun searchAnime(
        @Query("q") keywords: String,
        @Query("page") page: Int = 1,
        @Query("genre") genre: Int = 12,
        @Query("genre_exclude") genreExclude: Int = 1,
        @Query("limit") limit: Int = 10,
        @Query("order_by") orderBy: String = "members",
        @Query("sort") sort: String = "desc"
    ) : Response<AnimeSearchResults>
}