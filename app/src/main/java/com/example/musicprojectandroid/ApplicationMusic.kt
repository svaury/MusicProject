package com.example.musicprojectandroid

import android.app.Application
import android.content.Context
import android.util.Log

class ApplicationMusic : Application() {


    companion object {

        lateinit  var appContext: Context

    }

    override fun onCreate() {
        super.onCreate()
        Log.i("Application Create","Application Create ")
        appContext = applicationContext
    }

}