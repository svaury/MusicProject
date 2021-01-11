package com.example.musicprojectandroid.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.musicprojectandroid.ui.viewmodels.MusicViewModel

class ViewModelFactory()  : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MusicViewModel::class.java)) {
            return MusicViewModel() as T
        }
        throw IllegalArgumentException("Unknown class name")
    }


}