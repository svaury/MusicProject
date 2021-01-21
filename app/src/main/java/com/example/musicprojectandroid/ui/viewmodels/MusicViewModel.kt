package com.example.musicprojectandroid.ui.viewmodels

import android.os.Bundle
import androidx.lifecycle.*
import com.example.musicprojectandroid.model.Music
import com.example.musicprojectandroid.repository.MusicRepository
import com.example.musicprojectandroid.utils.Data
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.HashMap

class MusicViewModel(val musicRepository: MusicRepository) : ViewModel() {


    init {
        getMusics()
    }
    companion object {
        const val IsDataRestored = "IsDataRestored"
    }

    val dataMusicList : MutableLiveData<Data<List<Music>>> = MutableLiveData()

    fun getMusics(){
        GlobalScope.launch(viewModelScope.coroutineContext + Dispatchers.IO){
            musicRepository.getAllMusicsFromApi().collect { value ->dataMusicList.postValue(value) }

        }
    }

    fun sortMusicByAlbumID(data : Data<List<Music>>) :  HashMap<Int,MutableList<Music>>{
        return data.data?.groupByTo(HashMap()) { music -> music.albumId }?: hashMapOf()
    }

}