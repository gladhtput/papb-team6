package com.dteti.animapp.repository

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dteti.animapp.common.exceptions.NoAnimeServiceException
import com.dteti.animapp.dto.AnimeSummary
import com.dteti.animapp.usecases.AnimeUseCases

class AnimePagingSource(
    private val animeUseCases: AnimeUseCases,
    private val keywords: String
) : PagingSource<Int, AnimeSummary>() {
    private val TAG = "AnimePagingSource"

    override fun getRefreshKey(state: PagingState<Int, AnimeSummary>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, AnimeSummary> {
        return try {
            val currentPage = params.key ?: 1
            Log.d(TAG, "Fetching page $currentPage")
            val searchResults = animeUseCases.searchAnime(keywords, currentPage)

            LoadResult.Page(
                data = searchResults.animes,
                prevKey = if (currentPage > 1) currentPage - 1 else null,
                nextKey = if (currentPage < searchResults.pageCount) currentPage + 1 else null
            )
        } catch(err: NoAnimeServiceException) {
            LoadResult.Error(
                err
            )
        }
    }
}