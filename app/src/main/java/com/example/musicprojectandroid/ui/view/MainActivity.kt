package com.example.musicprojectandroid.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicprojectandroid.R
import com.example.musicprojectandroid.model.Music
import com.example.musicprojectandroid.ui.adapter.AlbumAdapter
import com.example.musicprojectandroid.ui.viewmodels.MusicViewModel
import com.example.musicprojectandroid.utils.Data
import com.example.musicprojectandroid.utils.Status
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MusicViewModel
    private  lateinit var albumAdpter: AlbumAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupViewModel()
        setUpLiveData(savedInstanceState)
        setupUI()

    }
    private fun setupUI() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        albumAdpter = AlbumAdapter(hashMapOf(),arrayListOf())
        recyclerView.adapter = albumAdpter
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
                     displayData(it)

                 }
                 Status.SUCCESS -> {
                    displayData(it)
                 }
                 Status.LOADING -> {
                     Log.i("LOADING", "LOADING "+ it.data?.size)
                 }
             }

        })
    }

    private fun displayData(data : Data<List<Music>>){
        recyclerView.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
        val musicSortByAlbumId = data.data?.groupByTo(HashMap()) { music -> music.albumId }
        albumAdpter.updateAlbumMap(musicSortByAlbumId)
    }
}
