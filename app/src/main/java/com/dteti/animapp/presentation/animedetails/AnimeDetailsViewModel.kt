package com.dteti.animapp.presentation.animedetails

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import androidx.room.Room
import com.dteti.animapp.dto.UriType
import com.dteti.animapp.services.animeapiservice.AnimeApiService
import com.dteti.animapp.services.persistenceservice.room.AnimAppDatabase
import com.dteti.animapp.usecases.AnimeUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnimeDetailsViewModel @Inject constructor(
    private val useCases: AnimeUseCases,
    private val animeApiService: AnimeApiService): ViewModel() {

    private var _animeId: Int? = null
    var animeId: Int? = _animeId
        set(value) {
            if (value != null) {
                loadAnimeDetails(value)
                _animeId = value
            }
        }

    var onBackPressed: (() -> Unit)? = null

    private val _posterImageUri = MutableLiveData<String>()
    val posterImageUri: LiveData<String> = _posterImageUri

    private val _posterImageUriType = MutableLiveData<UriType>()
    val posterImageUriType: LiveData<UriType> = _posterImageUriType

    private val _title = MutableLiveData<String>()
    val title: LiveData<String> = _title

    private val _rating = MutableLiveData<String>()
    val rating: LiveData<String> = _rating

    private val _episodeCount = MutableLiveData<Int>()
    val episodeCount: LiveData<Int> = _episodeCount

    private val _airingStatus = MutableLiveData<String>()
    val airingStatus: LiveData<String> = _airingStatus

    private val _synopsis = MutableLiveData<String>()
    val synopsis: LiveData<String> = _synopsis

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> = _isFavorite

    private fun loadAnimeDetails(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val animeDetails = useCases.readAnimeById(id)

            if (animeDetails != null) {
                _posterImageUri.postValue(animeDetails.mainPictureUri)
                _posterImageUriType.postValue(animeDetails.mainPictureUriType)
                _title.postValue(animeDetails.title)
                _rating.postValue(animeDetails.ageRating)
                _episodeCount.postValue(animeDetails.episodeCount)
                _airingStatus.postValue(animeDetails.airingStatus)
                _synopsis.postValue(animeDetails.synopsis)
                _isFavorite.postValue(animeDetails.isFavorite)
            } else {
                // TODO: Show a toast or something.
            }
        }
    }

    fun toggleFavorite() {
        _isFavorite.value = !_isFavorite.value!!

        commitFavorite()
    }

    fun commitFavorite() {
        Log.d("AnimeDetailsViewModel","Committing favorite anime.")

        viewModelScope.launch(Dispatchers.IO) {
            useCases.setFavorite(_animeId!!, _isFavorite.value!!)
            Log.d("AnimeDetailsViewModel", "Favorite status committed.")

            if (!animeApiService.isAvailable()) {
                viewModelScope.launch(Dispatchers.Main) {
                    onBackPressed?.invoke()
                }
            }
        }
    }
}