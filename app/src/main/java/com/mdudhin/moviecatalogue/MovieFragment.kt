package com.mdudhin.moviecatalogue


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mdudhin.moviecatalogue.adapter.ListMovieAdapter
import com.mdudhin.moviecatalogue.detail.DetailMovieActivity
import com.mdudhin.moviecatalogue.entity.Movie
import com.mdudhin.moviecatalogue.model.MovieViewModel
import kotlinx.android.synthetic.main.fragment_movie.*

class MovieFragment : Fragment() {

    private lateinit var adapter: ListMovieAdapter
    private lateinit var mainViewModel: MovieViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showRecyclerList()
    }

    private fun showRecyclerList(){
        adapter = ListMovieAdapter(activity)
        adapter.notifyDataSetChanged()

        rv_movies.layoutManager = LinearLayoutManager(context)
        rv_movies.adapter = adapter

        getData()
    }

    private fun getData(){
        mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            MovieViewModel::class.java)

        mainViewModel.setMovie()
        rv_movies.visibility = View.GONE
        showLoading(true)

        displayData()
    }

    private fun displayData(){
        mainViewModel.getMovies().observe(viewLifecycleOwner, Observer { movieItems ->
            if (movieItems != null){
                adapter.setData(movieItems)
                rv_movies.visibility = View.VISIBLE
                showLoading(false)

            }
        })
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }

}
