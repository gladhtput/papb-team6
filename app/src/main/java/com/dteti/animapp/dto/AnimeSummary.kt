package com.dteti.animapp.dto

import com.google.gson.annotations.SerializedName

data class AnimeSummary (
    val id: Int,
    val mainPictureUri: String,
    val mainPictureUriType: UriType,
    val score: String,
    val title: String,
    val synopsis: String
)