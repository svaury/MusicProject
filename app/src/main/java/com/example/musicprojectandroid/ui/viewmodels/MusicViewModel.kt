package com.example.musicprojectandroid.ui.viewmodels

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.musicprojectandroid.model.Music
import com.example.musicprojectandroid.repository.MusicRepository
import com.example.musicprojectandroid.utils.Data
import kotlinx.coroutines.Dispatchers
import java.util.*

class MusicViewModel : ViewModel() {

    companion object {
        const val isDataRestored = "isDataRestored"
    }

    val musicRepository = MusicRepository()

    fun getAllMusic(bundle: Bundle?) : LiveData<Data<List<Music>>>{

        return if(bundle?.getBoolean(isDataRestored) != null && bundle.getBoolean(isDataRestored)){
            musicRepository.getMusciFromDb().asLiveData(viewModelScope.coroutineContext + Dispatchers.IO )

        }else{
            musicRepository.getAllMusicsFromApi().asLiveData(viewModelScope.coroutineContext +Dispatchers.IO )
        }
    }


    fun saveState(bundle: Bundle){
        bundle.putBoolean(isDataRestored,true)
    }

}