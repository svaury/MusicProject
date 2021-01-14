package com.example.musicprojectandroid

import com.example.musicprojectandroid.model.Music
import com.example.musicprojectandroid.repository.MusicRepository
import com.example.musicprojectandroid.repository.local.dao.MusicDao
import com.example.musicprojectandroid.repository.local.entity.MusicEntity
import com.example.musicprojectandroid.repository.remote.ToApiModelMapper
import com.example.musicprojectandroid.repository.remote.services.MusicApiService
import com.example.musicprojectandroid.utils.Data
import com.example.musicprojectandroid.utils.Status
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockkClass
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import org.junit.rules.TestRule
import java.lang.RuntimeException

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class MockRespositoryTest {

    private val musicDao = mockkClass(MusicDao::class)
    private val musicService = mockkClass(MusicApiService::class)

    @Test
    fun testRepository() =runBlockingTest {

        every {

            musicDao.getAllMusics()
        } returns mockLocalMusicList()

        coEvery{
           musicService.getMusics()
        }returns mockRemoteMusicList()

        every {
            musicDao.deleteAndInsert(mockLocalMusicList())
        }returns Unit

        val musicRepository = MusicRepository(musicDao,musicService)

        val result = musicRepository.getAllMusicsFromApi().toList()
        assertEquals(result[0].status, Status.LOADING)
        assertEquals(result[1].status, Status.SUCCESS)

        assertEquals(result[0].data?.size,0)
        assertEquals(result[1].data?.size,mockRemoteMusicList().size)
        assertEquals(result[1].data, mockRemoteMusicList())
        assertNotEquals(result[1].data, mockRemote2MusicList())


        coEvery{
            musicService.getMusics()
        }throws RuntimeException()

        val result2 = musicRepository.getAllMusicsFromApi().toList()

        assertEquals(result2[1].data?.size,mockLocalMusicList().size)
        assertEquals(result2[1].data, mockLocalMusicList().map { musicEntity -> ToApiModelMapper.toApiModel(musicEntity)})

    }


    private fun mockRemoteMusicList():List<Music>{
        val listMusic = arrayListOf<Music>()

        val music1 = Music(1,1,"title1",null,null)
        val music2 = Music(1,2,"title2",null,null)
        val music3 = Music(2,3,"title3",null,null)
        val music4 = Music(2,4,"title4",null,null)


        listMusic.add(music1)
        listMusic.add(music2)
        listMusic.add(music3)
        listMusic.add(music4)


        return listMusic

    }

    private fun mockRemote2MusicList():List<Music>{
        val listMusic = arrayListOf<Music>()

        val music1 = Music(1,1,"title1",null,null)
        val music2 = Music(1,2,"title2",null,null)
        val music3 = Music(2,3,"title8",null,null)
        val music4 = Music(2,4,"title4",null,null)


        listMusic.add(music1)
        listMusic.add(music2)
        listMusic.add(music3)
        listMusic.add(music4)


        return listMusic

    }

    fun mockLocalMusicList():List<MusicEntity>{
        val listMusic = arrayListOf<MusicEntity>()

        val music1 = MusicEntity(1,1,"title1",null,null)
        val music2 = MusicEntity(1,2,"title2",null,null)
        val music3 = MusicEntity(2,3,"title2",null,null)

        listMusic.add(music1)
        listMusic.add(music2)
        listMusic.add(music3)


        return listMusic

    }



}
