package com.example.musicprojectandroid.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.musicprojectandroid.model.Music
import com.example.musicprojectandroid.repository.MusicRepository

class MusicViewModel : ViewModel() {
     val musicRepository = MusicRepository()

    val musicList: LiveData<List<Music>> =
        musicRepository.getAllMusicsFromApi().asLiveData(viewModelScope.coroutineContext)
}