package com.dteti.animapp.services.animeapiservice

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.dteti.animapp.services.animeapiservice.models.AnimeDetails
import com.dteti.animapp.services.animeapiservice.models.AnimeSearchResults
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class JikanAnimeApiService(private val context: Context) : AnimeApiService {
    private val jikanApi = Retrofit.Builder()
        .baseUrl("https://api.jikan.moe/v3/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(JikanApi::class.java)

    override suspend fun getAnimeDetailsById(id: Int): AnimeDetails? {
        val response = jikanApi.getAnimeDetailsById(id)

        return response.body()
    }

    override suspend fun searchAnime(keywords: String, page: Int): AnimeSearchResults? {
        val response = jikanApi.searchAnime(
            keywords,
            page,
            orderBy = if (keywords == "") "members" else "",
            sort = if (keywords == "") "desc" else "")
        val body = response.body()

        return body
    }

    override fun isAvailable(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                    return true
                }
            }
        }
        return false
    }
}