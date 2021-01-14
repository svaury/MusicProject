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

class MusicViewModel(val musicRepository: MusicRepository) : ViewModel() {

    companion object {
        const val IsDataRestored = "IsDataRestored"
    }

    val dataMusicList : MutableLiveData<Data<List<Music>>> = MutableLiveData()

    fun getMusics(bundle: Bundle?){
        GlobalScope.launch(viewModelScope.coroutineContext + Dispatchers.IO){
             getAllMusic(bundle).collect { value ->dataMusicList.postValue(value) }

        }
    }

    fun getAllMusic(bundle: Bundle?): Flow<Data<List<Music>>> {
        return if(bundle?.getBoolean(IsDataRestored) != null && bundle.getBoolean(IsDataRestored)){
            println("getMusicFromDb")
            musicRepository.getMusciFromDb()

        }else{
            println("getMusicFromApi")

            musicRepository.getAllMusicsFromApi()
        }
    }




    fun saveState(bundle: Bundle){
        bundle.putBoolean(IsDataRestored,true)
    }

}