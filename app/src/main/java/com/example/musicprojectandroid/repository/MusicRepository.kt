package com.example.musicprojectandroid.repository

import androidx.room.Room
import com.example.musicprojectandroid.ApplicationMusic
import com.example.musicprojectandroid.model.Music
import com.example.musicprojectandroid.repository.local.DbHelper
import com.example.musicprojectandroid.repository.local.dao.MusicDao
import com.example.musicprojectandroid.repository.remote.RetrofitBuilder
import com.example.musicprojectandroid.repository.remote.ToApiModelMapper
import com.example.musicprojectandroid.repository.remote.services.MusicApiService
import com.example.musicprojectandroid.utils.Data
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class MusicRepository(val musicDao: MusicDao,val musicApiService: MusicApiService) {



    fun getAllMusicsFromApi(): Flow<Data<List<Music>>> = flow {
        emit(Data.loading(emptyList()))
        try {
            val result = musicApiService.getMusics()
            emit(Data.success(result))
            musicDao.deleteAndInsert(result.map { music -> ToDbModelMapper.toDbModel(music) })
        } catch (throwable: Throwable) {
            emit(Data.error( musicDao.getAllMusics().map { musicDao -> ToApiModelMapper.toApiModel(musicDao) },throwable.message?: ""))
        }
    }

    fun getMusciFromDb(): Flow<Data<List<Music>>> = flow{
        emit(Data.success(musicDao.getAllMusics().map { musicDao -> ToApiModelMapper.toApiModel(musicDao)}))

    }

}