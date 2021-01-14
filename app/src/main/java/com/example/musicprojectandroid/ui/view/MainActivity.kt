package com.example.musicprojectandroid.ui.view

import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.musicprojectandroid.ApplicationMusic
import com.example.musicprojectandroid.R
import com.example.musicprojectandroid.model.Music
import com.example.musicprojectandroid.repository.MusicRepository
import com.example.musicprojectandroid.repository.local.DbHelper
import com.example.musicprojectandroid.repository.remote.RetrofitBuilder
import com.example.musicprojectandroid.ui.ViewModelFactory
import com.example.musicprojectandroid.ui.adapter.MusicExpandableListAdapter
import com.example.musicprojectandroid.ui.viewmodels.MusicViewModel
import com.example.musicprojectandroid.utils.Data
import com.example.musicprojectandroid.utils.Status
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MusicViewModel
    private  lateinit var albumAdpter: MusicExpandableListAdapter

    private val LIST_STATE_KEY = "listState"
    private val LIST_POSITION_KEY = "listPosition"
    private val ITEM_POSITION_KEY = "itemPosition"

    private var mListState: Parcelable? = null
    private var mListPosition = 0
    private var mItemPosition = 0

    var musicSortByAlbumId : HashMap<Int,MutableList<Music>> ?= null

    var state: Parcelable? = null

    var mLayoutManager: RecyclerView.LayoutManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mLayoutManager =  LinearLayoutManager(this)
        setupViewModel()
        setupUI()
        setUpLiveData(savedInstanceState)
        savedInstanceState?.let {
            mListState = it.getParcelable(LIST_STATE_KEY);
            mListPosition = it.getInt(LIST_POSITION_KEY);
            mItemPosition = it.getInt(ITEM_POSITION_KEY);

        }

    }

    override fun onResume() {
        super.onResume()
        if (state != null) {
            mLayoutManager?.onRestoreInstanceState(state);
        }
    }

    override fun onPause() {
        super.onPause()
        state = mLayoutManager?.onSaveInstanceState()
    }
    private fun setupUI() {
        albumAdpter = MusicExpandableListAdapter(this,arrayListOf(), hashMapOf())
        recyclerView.setAdapter(albumAdpter)
        recyclerView.setOnChildClickListener { parent, v, groupPosition, childPosition, id ->
            MusicDetailActivity.start(this,albumAdpter.getChild(groupPosition,childPosition)!!)
            true

        }
        pullToRefresh.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
            setUpLiveData(null)
            mListState = null
            mListPosition = 0
            mItemPosition = 0

        })

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        viewModel.saveState(outState)

        mListState = recyclerView.onSaveInstanceState()
        outState.putParcelable(LIST_STATE_KEY, mListState)

        // Save position of first visible item
        // Save position of first visible item
        mListPosition = recyclerView.firstVisiblePosition
        outState.putInt(LIST_POSITION_KEY, mListPosition)

        // Save scroll position of item
        // Save scroll position of item
        val itemView: View = recyclerView.getChildAt(0)
        mItemPosition = itemView?.top ?: 0
        outState.putInt(ITEM_POSITION_KEY, mItemPosition)

    }


    private fun setupViewModel() {

        val musicRepository = MusicRepository(Room.databaseBuilder(
                ApplicationMusic.appContext,
                DbHelper::class.java, "database-musics"
        ).build().musicDao(), RetrofitBuilder.musicApiService)

        viewModel = ViewModelProviders.of(this,ViewModelFactory(musicRepository)).get(MusicViewModel::class.java)
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
        pullToRefresh.isRefreshing = false
        musicSortByAlbumId = data.data?.groupByTo(HashMap()) { music -> music.albumId }
        //albumAdpter = MusicExpandableListAdapter(this,ArrayList(musicSortByAlbumId!!.keys),musicSortByAlbumId)
        //recyclerView.setAdapter(albumAdpter)
        albumAdpter.updateAlbumMap(musicSortByAlbumId)

        mListState?.let {  recyclerView.onRestoreInstanceState(mListState)}
        recyclerView.setSelectionFromTop(mListPosition, mItemPosition)
    }
}
