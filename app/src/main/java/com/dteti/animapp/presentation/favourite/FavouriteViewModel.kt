package com.dteti.animapp.presentation.favourite

import android.app.Application
import androidx.lifecycle.*
import androidx.room.Room
import com.dteti.animapp.services.persistenceservice.room.AnimAppDatabase
import com.dteti.animapp.dto.AnimeSummary
import com.dteti.animapp.usecases.AnimeUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(private val animeUseCases: AnimeUseCases) : ViewModel() {
    private var _animes = MutableLiveData<List<AnimeSummary>>()
    val animes: LiveData<List<AnimeSummary>> = _animes

    init {
        loadFavoriteAnimes()
    }

    fun loadFavoriteAnimes() {
        viewModelScope.launch(Dispatchers.IO) {
            val favoriteAnimes = animeUseCases.readAllFavoriteAnimes()
            _animes.postValue(favoriteAnimes)
        }
    }
}