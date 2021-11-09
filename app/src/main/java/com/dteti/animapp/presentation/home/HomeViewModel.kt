package com.dteti.animapp.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dteti.animapp.dto.AnimeSummary
import com.dteti.animapp.usecases.AnimeUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val animeUseCases: AnimeUseCases) : ViewModel() {
    lateinit var searchKeywords: LiveData<String>

    private var _animes = MutableLiveData<List<AnimeSummary>>()
    val animes: LiveData<List<AnimeSummary>> = _animes

    fun search(keywords: String): Boolean {
        viewModelScope.launch {
            _animes.postValue(animeUseCases.searchAnime(keywords, 1))
        }

        return false
    }

    init {
        search("")
    }
}