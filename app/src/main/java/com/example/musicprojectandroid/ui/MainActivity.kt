package com.example.musicprojectandroid.ui

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
import com.example.musicprojectandroid.ui.adapter.MusicListAdpter
import com.example.musicprojectandroid.ui.model.MusicAlbum
import com.example.musicprojectandroid.ui.model.TypeModelMusic
import com.example.musicprojectandroid.ui.viewmodels.MusicViewModel
import com.example.musicprojectandroid.utils.Status
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MusicViewModel
    private  lateinit var musicListAdpter: MusicListAdpter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupViewModel()
        setUpLiveData(savedInstanceState)
        setupUI()

    }
    private fun setupUI() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        musicListAdpter = MusicListAdpter(arrayListOf())
        recyclerView.adapter = musicListAdpter
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
                     musicListAdpter.updateMusicsList(it.data!!)

                 }
                 Status.SUCCESS -> {
                     recyclerView.visibility = View.VISIBLE
                     progressBar.visibility = View.GONE
                     val musicSortByAlbumId = it.data?.groupBy { music -> music.albumId }
                     val musicList = arrayListOf<MusicAlbum>()
                     musicSortByAlbumId?.let {map->
                         for(key in map.keys){
                             musicList.add(MusicAlbum(TypeModelMusic.ALBUM, null))
                             val musicToMusicAlbum = musicSortByAlbumId[key]?.map { music -> MusicAlbum(TypeModelMusic.TITLE,music) }
                             musicList.addAll(musicToMusicAlbum!!)
                         }
                     }


                     musicListAdpter.updateMusicsList(it.data!!)
                    Log.i("SUCCESS", "SUCCESS "+ it.data?.size)
                 }
                 Status.LOADING -> {
                     Log.i("LOADING", "LOADING "+ it.data?.size)
                 }
             }

        })
    }
}
