package com.example.musicprojectandroid.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.musicprojectandroid.R
import com.example.musicprojectandroid.ui.viewmodels.MusicViewModel
import com.example.musicprojectandroid.utils.Status

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MusicViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupViewModel()
        setUpLiveData(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        viewModel.saveState(outState)
    }


    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(this).get(MusicViewModel::class.java)
    }

    private fun setUpLiveData(bundle: Bundle?){
        viewModel.getAllMusic(bundle).observe(this, Observer {
             when(it.status){

                 Status.ERROR -> {
                     Log.i("ERROR", "ERROR "+ it?.message)

                 }
                 Status.SUCCESS -> {
                    Log.i("SUCCESS", "SUCCESS "+ it.data?.size)
                 }
                 Status.LOADING -> {

                 }


             }



        })
    }
}
