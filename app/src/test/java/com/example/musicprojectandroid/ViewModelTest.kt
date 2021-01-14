package com.example.musicprojectandroid

import com.example.musicprojectandroid.model.Music
import com.example.musicprojectandroid.repository.MusicRepository
import com.example.musicprojectandroid.ui.viewmodels.MusicViewModel
import com.example.musicprojectandroid.utils.Data

import io.mockk.every
import io.mockk.mockkClass
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest

import org.junit.Test

class ViewModelTest {


    private val  musicRepository = mockkClass(MusicRepository::class)

    @Test
    fun testViewModel() =runBlockingTest {

        every{
            musicRepository.getMusciFromDb()
        }returns flow {Data.success(mockLocalMusicList())}


        every{
            musicRepository.getAllMusicsFromApi()
        }returns flow {Data.success(mockRemoteMusicList())}

        val viewModel = MusicViewModel(musicRepository)

        musicRepository.getAllMusicsFromApi().collect{
           print(it.data)
        }

        val result = viewModel.getAllMusic(null).collect {

        }

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