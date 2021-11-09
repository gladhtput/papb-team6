package com.dteti.animapp.services.persistenceservice.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Anime (
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "main_picture_uri") val mainPicturePath: String,
    val title: String,
    val score: String,
    @ColumnInfo(name = "age_rating") val ageRating: String,
    @ColumnInfo(name = "episode_count") val episodeCount: Int,
    @ColumnInfo(name = "airing_status") val airingStatus: String,
    val synopsis: String
)