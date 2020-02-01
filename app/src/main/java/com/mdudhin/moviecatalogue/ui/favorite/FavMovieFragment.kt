package com.mdudhin.moviecatalogue.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager

import com.mdudhin.moviecatalogue.R
import com.mdudhin.moviecatalogue.adapter.FavMovieAdapter
import com.mdudhin.moviecatalogue.db.movie.FavMovieHelper
import com.mdudhin.moviecatalogue.entity.Movie
import com.mdudhin.moviecatalogue.helper.movie.MappingHelper
import kotlinx.android.synthetic.main.fragment_fav_movie.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FavMovieFragment : Fragment() {

    private lateinit var adapter: FavMovieAdapter
    private lateinit var favMovieHelper: FavMovieHelper

    companion object {
        private const val EXTRA_STATE = "EXTRA_STATE"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fav_movie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rv_movies.layoutManager = LinearLayoutManager(context)
        rv_movies.setHasFixedSize(true)
        adapter = FavMovieAdapter(activity)
        adapter.notifyDataSetChanged()

        rv_movies.adapter = adapter

        loadMoviesAsync()

        if (savedInstanceState == null) {
            // proses ambil data
            loadMoviesAsync()
        } else {
            val list = savedInstanceState.getParcelableArrayList<Movie>(EXTRA_STATE)
            if (list != null) {
                adapter.listFavMovies = list
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(EXTRA_STATE, adapter.listFavMovies)
    }

    private fun loadMoviesAsync(){
        GlobalScope.launch(Dispatchers.Main) {
            progressbar.visibility = View.VISIBLE
            val deferredNotes = async(Dispatchers.IO) {
                favMovieHelper = FavMovieHelper.getInstance(activity!!.applicationContext)
                favMovieHelper.open()
                val cursor = favMovieHelper.queryAll()
                MappingHelper.mapCursorToArrayList(cursor)
            }
            progressbar.visibility = View.INVISIBLE
            val notes = deferredNotes.await()
            if (notes.size > 0) {
                adapter.listFavMovies = notes
            } else {
                adapter.listFavMovies = ArrayList()
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
        favMovieHelper.close()
    }
}