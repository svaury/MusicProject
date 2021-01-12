package com.example.musicprojectandroid.repository.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.musicprojectandroid.model.Music
import com.example.musicprojectandroid.repository.local.dao.MusicDao
import com.example.musicprojectandroid.repository.local.entity.MusicEntity

@Database(entities = arrayOf(MusicEntity::class), version = 1)
abstract class DbHelper : RoomDatabase() {
    abstract fun musicDao(): MusicDao
}
