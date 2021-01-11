package com.example.musicprojectandroid.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.musicprojectandroid.model.Music
import com.example.musicprojectandroid.repository.MusicRepository
import com.example.musicprojectandroid.utils.Data
import kotlinx.coroutines.Dispatchers

class MusicViewModel : ViewModel() {
     val musicRepository = MusicRepository()

    val musicList: LiveData<Data<List<Music>>> = musicRepository.getAllMusicsFromApi().asLiveData(Dispatchers.IO + viewModelScope.coroutineContext)

}