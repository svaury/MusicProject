package com.example.musicprojectandroid.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.musicprojectandroid.R
import com.example.musicprojectandroid.model.Music
import kotlinx.android.synthetic.main.music_detail_layout.view.*


class MusicListAdpter(private val musics: ArrayList<Music>) : RecyclerView.Adapter<MusicListAdpter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(music: Music) {
            itemView.title_tv.text = music.title
            Glide.with(itemView.iv_music.context)
                .load(music.thumbnailUrl)
                .into(itemView.iv_music)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.music_detail_layout, parent, false))

    override fun getItemCount(): Int = musics.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(musics[position])
    }

    fun updateMusicsList(musicList: List<Music>) {
        musics.clear()
        musics.addAll(musicList)
        notifyDataSetChanged()
    }
}