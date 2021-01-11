package com.example.musicprojectandroid.repository.remote.services

import com.example.musicprojectandroid.model.Music
import retrofit2.http.GET

interface MusicApiService {

    @GET("img/shared/technical-test.json")
    suspend fun getMusics(): List<Music>
}