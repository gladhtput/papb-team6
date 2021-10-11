package com.dteti.animapp.ui.favourite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dteti.animapp.services.animeservice.Anime
import com.dteti.animapp.services.animeservice.AnimeService
import com.dteti.animapp.services.animeservice.JikanAnimeService
import kotlinx.coroutines.launch

class FavouriteViewModel : ViewModel() {

    private val service: AnimeService = JikanAnimeService()

    lateinit var searchKeywords: LiveData<String>

    private var _animes = MutableLiveData<List<Anime>>()
    val animes: LiveData<List<Anime>> = _animes

    fun search(keywords: String): Boolean {
        viewModelScope.launch {
            _animes.postValue(service.searchAnime(keywords, 1))
        }

        return false
    }

    init {
        search("")
    }
}