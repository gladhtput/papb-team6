package com.dteti.animapp.presentation.home

import android.util.Log
import android.view.View
import androidx.lifecycle.*
import com.dteti.animapp.dto.AnimeSummary
import com.dteti.animapp.services.animeapiservice.AnimeApiService
import com.dteti.animapp.usecases.AnimeUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val animeUseCases: AnimeUseCases, private val animeApiService: AnimeApiService) : ViewModel() {
    private var _animes = MutableLiveData<List<AnimeSummary>>()
    val animes: LiveData<List<AnimeSummary>> = _animes

    private var _noConnectionOverlayVisible = MutableLiveData<Boolean>()
    val noConnectionOverlayVisible: LiveData<Boolean> = _noConnectionOverlayVisible

    private var _mainOverlayVisible = MutableLiveData<Boolean>()
    val mainOverlayVisible: LiveData<Boolean> = _mainOverlayVisible

    private var _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private var _keywords = ""

    fun search(keywords: String): Boolean {
        _keywords = keywords

        viewModelScope.launch {
            _isLoading.value = true

            if (animeApiService.isAvailable()) {
                showMainOverlay()
                _animes.postValue(animeUseCases.searchAnime(keywords, 1))
            } else {
                showNoConnectionOverlay()
            }

            _isLoading.value = false
        }

        return false
    }

    fun refresh() {
        search(_keywords)
    }

    fun checkConnection() {
        viewModelScope.launch(Dispatchers.Main) {
            if (animeApiService.isAvailable()) {
                showMainOverlay()
                search("")
            }
        }
    }

    private fun showNoConnectionOverlay() {
        _noConnectionOverlayVisible.postValue(true)
        _mainOverlayVisible.postValue(false)
    }

    private fun showMainOverlay() {
        _noConnectionOverlayVisible.postValue(false)
        _mainOverlayVisible.postValue(true)
    }

    init {
        search("")
    }
}