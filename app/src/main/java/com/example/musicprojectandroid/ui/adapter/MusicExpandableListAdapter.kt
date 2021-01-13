package com.example.musicprojectandroid.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.musicprojectandroid.R
import com.example.musicprojectandroid.model.Music
import com.squareup.picasso.Picasso


class MusicExpandableListAdapter( val context: Context,val  albumIdsList : ArrayList<Int>,
                                  val musicsMap: HashMap<Int, MutableList<Music>>): BaseExpandableListAdapter() {



    override fun getGroup(groupPosition: Int): Any {
        return this.albumIdsList[groupPosition]
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
       return true
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun getGroupView(groupPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup?): View {
        var convertView = convertView
        if(convertView == null){
            val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = layoutInflater.inflate(R.layout.album_layout, null)
        }

        return  convertView!!    }

    override fun getChildrenCount(groupPosition: Int): Int {
       return musicsMap[this.albumIdsList[groupPosition]]!!.size
    }

    override fun getChild(groupPosition: Int, childPosition: Int): Music? {
        return musicsMap[albumIdsList[groupPosition]]?.get(childPosition)
    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    override fun getChildView(groupPosition: Int, childPosition: Int, isLastChild: Boolean, convertView: View?, parent: ViewGroup?): View {
        var convertView = convertView
        if(convertView == null){
            val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = layoutInflater.inflate(R.layout.music_detail_layout, null)
        }

        val music = getChild(groupPosition,childPosition) as Music
        val ivMusic = convertView?.findViewById<ImageView>(R.id.iv_music)
        val tvTitle = convertView?.findViewById<TextView>(R.id.title_tv)

        Picasso.get().load(music.thumbnailUrl).placeholder(R.drawable.ic_camera_alt_24dp).into(ivMusic)
        tvTitle?.text = music.title

        Log.i("ChildView","ChildView "+ music.title )

        return  convertView!!
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {

        return  childPosition.toLong()
    }

    override fun getGroupCount(): Int {
       return albumIdsList.size
    }


    fun updateAlbumMap(albums:  HashMap<Int,MutableList<Music>>?) {

        albums?.let {
            musicsMap.clear()
            musicsMap.putAll(albums)
            albumIdsList.clear()
            albumIdsList.addAll(albums.keys)
            notifyDataSetInvalidated()
        }
    }
}