package com.dteti.animapp.dto

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

data class AnimeDetails (
    val id: Int,
    val mainPictureUri: String,
    val mainPictureUriType: UriType,
    val title: String,
    val score: String,
    val ageRating: String,
    val episodeCount: Int,
    val airingStatus: String,
    val synopsis: String,
    val isFavorite: Boolean
)