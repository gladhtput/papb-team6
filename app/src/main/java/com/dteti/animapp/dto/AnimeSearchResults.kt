package com.dteti.animapp.dto

data class AnimeSearchResults (
    val animes: List<AnimeSummary>,
    val pageCount: Int
)