package com.example.musicprojectandroid

import android.app.Application
import android.content.Context

class ApplicationMusic : Application() {

    override fun onCreate() {
        super.onCreate()
        ApplicationMusic.appContext = applicationContext
    }

    companion object {

        lateinit  var appContext: Context

    }
}