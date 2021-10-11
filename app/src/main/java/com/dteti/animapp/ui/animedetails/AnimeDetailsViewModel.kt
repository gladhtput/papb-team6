package com.dteti.animapp.ui.animedetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dteti.animapp.services.animeservice.AnimeService
import com.dteti.animapp.services.animeservice.JikanAnimeService
import kotlinx.coroutines.launch

class AnimeDetailsViewModel: ViewModel() {
    private val service: AnimeService = JikanAnimeService()

    private val _posterImageUrl = MutableLiveData<String>()
    val posterImageUrl: LiveData<String> = _posterImageUrl

    private val _title = MutableLiveData<String>()
    val title: LiveData<String> = _title

    private val _rating = MutableLiveData<String>()
    val rating: LiveData<String> = _rating

    private val _episodeCount = MutableLiveData<String>()
    val episodeCount: LiveData<String> = _episodeCount

    private val _airingStatus = MutableLiveData<String>()
    val airingStatus: LiveData<String> = _airingStatus

    private val _synopsis = MutableLiveData<String>()
    val synopsis: LiveData<String> = _synopsis

    private fun loadAnimeDetails() {
        viewModelScope.launch {

        }
    }
}