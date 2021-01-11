package com.example.musicprojectandroid.repository.local.dao

import androidx.room.*
import com.example.musicprojectandroid.repository.local.entity.MusicEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface MusicDao {

    @Transaction
    fun deleteAndInsert(musics: List<MusicEntity>) {
        deleteAllMusic()
        insertAllMusic(musics)
    }
    @Query("SELECT * FROM music")
    fun getAllMusics(): List<MusicEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllMusic(musics: List<MusicEntity>)

    @Query("DELETE FROM music")
    fun deleteAllMusic()

}
