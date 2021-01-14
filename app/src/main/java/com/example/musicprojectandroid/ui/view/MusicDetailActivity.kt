package com.example.musicprojectandroid.ui.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.musicprojectandroid.R
import com.example.musicprojectandroid.model.Music
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.music_detail_activity_layout.*

class MusicDetailActivity :  AppCompatActivity() {

    companion object{
        var music : Music? = null

        val MUSIC = "MUSIC"

        fun start(context : Context, music: Music) {
            val intent = Intent(context, MusicDetailActivity::class.java).apply {
                putExtra(MUSIC, music)
            }
            context.startActivity(intent)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.music_detail_activity_layout)
        if(savedInstanceState != null && savedInstanceState.containsKey(MUSIC)){
            music = savedInstanceState.getParcelable(MUSIC)
        }else {
            music = intent.getParcelableExtra(MUSIC)
        }
        Picasso.get().load(music?.url).placeholder(R.drawable.ic_camera_100dp).into(iv_music_detail)
        title_tv_detail.text = music?.title


    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(MUSIC,music)

    }
}