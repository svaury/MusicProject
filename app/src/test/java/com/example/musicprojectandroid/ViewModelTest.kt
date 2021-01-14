package com.example.musicprojectandroid

import android.os.Bundle
import com.example.musicprojectandroid.model.Music
import com.example.musicprojectandroid.repository.MusicRepository
import com.example.musicprojectandroid.ui.viewmodels.MusicViewModel
import com.example.musicprojectandroid.utils.Data

import io.mockk.every
import io.mockk.mockkClass
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runBlockingTest

import org.junit.Test

class ViewModelTest {


    private val  musicRepository = mockkClass(MusicRepository::class)
    private val bundle = mockkClass(Bundle::class)

    @Test
    fun testViewModel() =runBlockingTest {

        every{
            musicRepository.getMusciFromDb()
        }returns flow {emit(Data.success(mockLocalMusicList()))}


        every{
            musicRepository.getAllMusicsFromApi()
        }returns flow { emit(Data.success(mockRemoteMusicList()))}

        val viewModel = MusicViewModel(musicRepository)

        val result = viewModel.getAllMusic(null).toList()

        assertEquals(result[0].data,mockRemoteMusicList())

        every {
            bundle.getBoolean(MusicViewModel.IsDataRestored)

        }returns true


        val result2 = viewModel.getAllMusic(bundle).toList()

        assertEquals(result2[0].data,mockLocalMusicList())


    }


    private fun mockRemoteMusicList():List<Music>{
        val listMusic = arrayListOf<Music>()

        val music1 = Music(1,1,"title1",null,null)
        val music2 = Music(1,2,"title2",null,null)

        listMusic.add(music1)
        listMusic.add(music2)

        return listMusic

    }

    private fun mockLocalMusicList():List<Music>{
        val listMusic = arrayListOf<Music>()

        val music1 = Music(1,1,"title1",null,null)
        val music2 = Music(1,2,"title2",null,null)
        val music3 = Music(2,3,"title3",null,null)


        listMusic.add(music1)
        listMusic.add(music2)
        listMusic.add(music3)
        return listMusic

    }


}