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

class MusicViewModel(val musicRepository: MusicRepository) : ViewModel() {

    companion object {
        const val IsDataRestored = "IsDataRestored"
    }


    fun getAllMusic(bundle: Bundle?) : LiveData<Data<List<Music>>>{

        return if(bundle?.getBoolean(IsDataRestored) != null && bundle.getBoolean(IsDataRestored)){
            musicRepository.getMusciFromDb().asLiveData(viewModelScope.coroutineContext + Dispatchers.IO )

        }else{
            musicRepository.getAllMusicsFromApi().asLiveData(viewModelScope.coroutineContext +Dispatchers.IO )
        }
    }


    fun saveState(bundle: Bundle){
        bundle.putBoolean(IsDataRestored,true)
    }

}