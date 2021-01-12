package com.example.musicprojectandroid.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.musicprojectandroid.R
import com.example.musicprojectandroid.model.Music
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.music_detail_layout.view.*


class MusicListAdpter(private val musics: ArrayList<Music>) : RecyclerView.Adapter<MusicListAdpter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(music: Music) {
            itemView.title_tv.text = music.title
            Picasso.get().load(music.thumbnailUrl).placeholder(R.drawable.ic_camera_alt_24dp).into(itemView.iv_music)
           /* Glide.with(itemView.iv_music.context)
                .load(music.thumbnailUrl)
                .placeholder(R.drawable.ic_camera_alt_24dp)
                .into(itemView.iv_music)*/

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.music_detail_layout, parent, false))

    override fun getItemCount(): Int = musics.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(musics[position])
    }

    fun updateMusicsList(musicList: List<Music>) {
        Log.i("MusicSuccess","MusicSuccess "+ musicList.size)
        musics.clear()
        musics.addAll(musicList)
        notifyDataSetChanged()
    }
}