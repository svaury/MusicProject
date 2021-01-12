package com.example.musicprojectandroid.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.musicprojectandroid.R
import com.example.musicprojectandroid.model.Music

import kotlinx.android.synthetic.main.album_detail_layout.view.*


class AlbumAdapter(private val albumMap: HashMap<Int,List<Music>>, val keys:ArrayList<Int>) : RecyclerView.Adapter<AlbumAdapter.ViewHolder>() {


    override fun onBindViewHolder(holder: AlbumAdapter.ViewHolder, position: Int) {
        val musics = albumMap[keys[position]]
        holder.bind(musics!!)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(musicList:List<Music>) {
            itemView.album_recyclerView.layoutManager = LinearLayoutManager(itemView.album_recyclerView.context)
            val musicListAdapter = MusicListAdpter(arrayListOf())

            musicListAdapter.updateMusicsList(musicList)
            itemView.album_recyclerView.adapter = musicListAdapter
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.album_detail_layout, parent, false))

    override fun getItemCount(): Int = keys.size

    fun updateAlbumMap(albums:  HashMap<Int,MutableList<Music>>?) {

        albums?.let {
            albumMap.clear()
            albumMap.putAll(albums)
            keys.clear()
            keys.addAll(albums.keys)

            notifyDataSetChanged()
        }

    }
}