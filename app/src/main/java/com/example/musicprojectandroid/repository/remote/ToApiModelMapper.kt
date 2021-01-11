package com.example.musicprojectandroid.repository.remote

import com.example.musicprojectandroid.model.Music
import com.example.musicprojectandroid.repository.local.entity.MusicEntity

object ToApiModelMapper {

    fun toApiModel(dbModel : MusicEntity ) : Music{

         return Music(dbModel.id,dbModel.albumId,dbModel.title,dbModel.url, dbModel.thumbnailUrl)
    }
}