package com.example.musicprojectandroid.ui.view

import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
import com.google.android.material.snackbar.Snackbar
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

    lateinit var musicLiveData : MutableLiveData<Data<List<Music>>>

    var mLayoutManager: RecyclerView.LayoutManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mLayoutManager =  LinearLayoutManager(this)
        setupViewModel()
        setupUI()
        setUpLiveData(savedInstanceState)
        viewModel.getMusics(savedInstanceState)
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
        expandableListView.setAdapter(albumAdpter)
        expandableListView.setOnChildClickListener { parent, v, groupPosition, childPosition, id ->
            MusicDetailActivity.start(this,albumAdpter.getChild(groupPosition,childPosition)!!)
            true

        }
        pullToRefresh.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
            viewModel.getMusics(null)
            mListState = null
            mListPosition = 0
            mItemPosition = 0

        })

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        viewModel.saveState(outState)

        mListState =expandableListView.onSaveInstanceState()
        outState.putParcelable(LIST_STATE_KEY, mListState)

        // Save position of first visible item
        // Save position of first visible item
        mListPosition = expandableListView.firstVisiblePosition
        outState.putInt(LIST_POSITION_KEY, mListPosition)

        // Save scroll position of item
        // Save scroll position of item
        val itemView: View = expandableListView.getChildAt(0)
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

        viewModel.dataMusicList.observe(this, Observer {
            when(it.status){
                Status.ERROR -> {
                    displayData(it)

                    Snackbar.make(constraintLayout,it.message?:"Une erreur est survenue" ,Snackbar.LENGTH_LONG).show()

                }
                Status.SUCCESS -> {
                    displayData(it)


                }
                Status.LOADING -> {
                }
            }

        })

    }

    private fun displayData(data : Data<List<Music>>){

        expandableListView.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
        pullToRefresh.isRefreshing = false
        musicSortByAlbumId = data.data?.groupByTo(HashMap()) { music -> music.albumId }
        //albumAdpter = MusicExpandableListAdapter(this,ArrayList(musicSortByAlbumId!!.keys),musicSortByAlbumId)
        //recyclerView.setAdapter(albumAdpter)
        albumAdpter.updateAlbumMap(musicSortByAlbumId)
        data.data?.let {
            if(it.isNotEmpty()){
                expandableListView.expandGroup(0)
            }
        }

        mListState?.let {  expandableListView.onRestoreInstanceState(mListState)}
        expandableListView.setSelectionFromTop(mListPosition, mItemPosition)
    }
}
