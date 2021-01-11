package com.example.musicprojectandroid.repository.remote

import com.example.musicprojectandroid.repository.remote.services.MusicApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitBuilder {

    private const val BASE_URL = "https://static.leboncoin.fr/"

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val musicApiService: MusicApiService = getRetrofit().create(MusicApiService::class.java)

}