package com.dteti.animapp.presentation.home

import android.util.Log
import android.view.View
import androidx.lifecycle.*
import androidx.paging.*
import com.dteti.animapp.dto.AnimeSummary
import com.dteti.animapp.repository.AnimePagingSource
import com.dteti.animapp.services.animeapiservice.AnimeApiService
import com.dteti.animapp.usecases.AnimeUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val animeUseCases: AnimeUseCases,
    private val animeApiService: AnimeApiService
) : ViewModel() {

    private var _noConnectionOverlayVisible = MutableLiveData<Boolean>()
    val noConnectionOverlayVisible: LiveData<Boolean> = _noConnectionOverlayVisible

    private var _mainOverlayVisible = MutableLiveData<Boolean>()
    val mainOverlayVisible: LiveData<Boolean> = _mainOverlayVisible

    private var _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private var _keywords = ""

    private var pagingSource: AnimePagingSource? = null
    private val pager = Pager(PagingConfig(pageSize = 10)) {
        val newPagingSource = AnimePagingSource(animeUseCases, _keywords)
        pagingSource = newPagingSource

        return@Pager newPagingSource
    }
    val flow = pager.flow.cachedIn(viewModelScope)

    init {
        search("")
    }

    fun search(keywords: String): Boolean {
        _keywords = keywords

        if (animeApiService.isAvailable()) {
            showMainOverlay()

            pagingSource?.invalidate()
        } else {
            showNoConnectionOverlay()
        }

        _isLoading.value = false
        return false
    }

    fun refresh() {
        search(_keywords)
    }

    fun checkConnection() {
        viewModelScope.launch(Dispatchers.Main) {
            if (animeApiService.isAvailable()) {
                showMainOverlay()
                refresh()
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
}