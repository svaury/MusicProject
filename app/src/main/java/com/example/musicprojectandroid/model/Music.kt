package com.example.musicprojectandroid.model

data class Music (
    val id: Int,
    val albumId : Int,
    val title: String? = "",
    val url: String? = "",
    val thumbnailUrl : String ?= ""
)