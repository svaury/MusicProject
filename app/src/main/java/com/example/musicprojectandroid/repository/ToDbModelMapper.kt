package com.example.musicprojectandroid.repository

import com.example.musicprojectandroid.model.Music
import com.example.musicprojectandroid.repository.local.entity.MusicEntity

object ToDbModelMapper{

    fun toDbModel(music: Music):MusicEntity{

        return MusicEntity(music.id, music.albumId , music.title, music.url, music.thumbnailUrl)
    }

}