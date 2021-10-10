package com.dteti.animapp.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.dteti.animapp.services.animeservice.Anime
import com.dteti.animapp.services.animeservice.AnimeService
import com.dteti.animapp.services.animeservice.JikanAnimeService
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class HomeViewModel() : ViewModel() {
    private val service: AnimeService = JikanAnimeService()

    private var _animes = MutableLiveData<List<Anime>>()
    val animes: LiveData<List<Anime>> = _animes

    fun applySearchKeywords(keywords: String) {
        viewModelScope.launch {
            _animes.postValue(service.searchAnime(keywords, 1))
        }
    }

    init {
        applySearchKeywords("")
    }
}