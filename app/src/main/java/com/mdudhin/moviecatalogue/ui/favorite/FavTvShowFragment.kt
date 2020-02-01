package com.mdudhin.moviecatalogue.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.mdudhin.moviecatalogue.R
import com.mdudhin.moviecatalogue.adapter.FavTvShowAdapter
import com.mdudhin.moviecatalogue.db.tvshow.FavTvShowHelper
import com.mdudhin.moviecatalogue.entity.TvShow
import com.mdudhin.moviecatalogue.helper.tvshow.MappingHelper.mapCursorToArrayList
import kotlinx.android.synthetic.main.fragment_fav_tv_show.*
import kotlinx.android.synthetic.main.fragment_fav_tv_show.progressbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FavTvShowFragment : Fragment() {

    private lateinit var adapter: FavTvShowAdapter
    private lateinit var favTvShowHelper: FavTvShowHelper

    companion object {
        private const val EXTRA_STATE = "EXTRA_STATE"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fav_tv_show, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rv_tv_shows.layoutManager = LinearLayoutManager(context)
        rv_tv_shows.setHasFixedSize(true)
        adapter = FavTvShowAdapter(activity)
        adapter.notifyDataSetChanged()

        rv_tv_shows.adapter = adapter

        loadMoviesAsync()

        if (savedInstanceState == null) {
            // proses ambil data
            loadMoviesAsync()
        } else {
            val list = savedInstanceState.getParcelableArrayList<TvShow>(EXTRA_STATE)
            if (list != null) {
                adapter.listFavTvShow = list
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(EXTRA_STATE, adapter.listFavTvShow)
    }

    private fun loadMoviesAsync(){
        GlobalScope.launch(Dispatchers.Main) {
            progressbar.visibility = View.VISIBLE
            val deferredNotes = async(Dispatchers.IO) {
                favTvShowHelper = FavTvShowHelper.getInstance(activity!!.applicationContext)
                favTvShowHelper.open()
                val cursor = favTvShowHelper.queryAll()
                mapCursorToArrayList(cursor)
            }
            progressbar.visibility = View.INVISIBLE
            val notes = deferredNotes.await()
            if (notes.size > 0) {
                adapter.listFavTvShow = notes
            } else {
                adapter.listFavTvShow = ArrayList()
                Toast.makeText(context, "Tidak Ada Data !!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        loadMoviesAsync()
    }

    override fun onDestroy() {
        super.onDestroy()
        favTvShowHelper.close()
    }
}
