package com.example.musicprojectandroid

import android.app.Application
import android.content.Context

class ApplicationMusic : Application() {


    companion object {

        lateinit  var appContext: Context

    }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
    }

}