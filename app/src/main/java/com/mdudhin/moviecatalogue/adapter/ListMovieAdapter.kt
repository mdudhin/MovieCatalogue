package com.mdudhin.moviecatalogue.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mdudhin.moviecatalogue.CustomOnItemClickListener
import com.mdudhin.moviecatalogue.R
import com.mdudhin.moviecatalogue.detail.DetailMovieActivity
import com.mdudhin.moviecatalogue.entity.Movie
import kotlinx.android.synthetic.main.item_row_movies.view.*

class ListMovieAdapter(private val activity: Activity?) : RecyclerView.Adapter<ListMovieAdapter.ListViewHolder>() {

    private val listMovie = ArrayList<Movie>()
    private val posterSize = "w185"

    fun setData(items: ArrayList<Movie>){
        listMovie.clear()
        listMovie.addAll(items)
        notifyDataSetChanged()
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(movie: Movie){

            val rating = movie.vote_average / 2

            with(itemView){
                Glide.with(itemView.context)
                    .load("https://image.tmdb.org/t/p/$posterSize/${movie.poster_path}")
                    .apply(RequestOptions().override(100,120))
                    .into(poster_movie)

                tv_movie_title.text = movie.title
                tv_release_date.text = movie.release_date
                tv_movie_overview.text = movie.overview
                rb_vote_average.rating = rating.toFloat()

                cv_item_movie.setOnClickListener(CustomOnItemClickListener(adapterPosition, object : CustomOnItemClickListener.OnItemClickCallback {
                    override fun onItemClicked(view: View, position: Int) {
                        val intent = Intent(activity, DetailMovieActivity::class.java)
                        intent.putExtra(DetailMovieActivity.EXTRA_POSITION, position)
                        intent.putExtra(DetailMovieActivity.EXTRA_MOVIE, movie)
                        activity?.startActivity(intent)
                    }
                }))
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): ListViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_row_movies, viewGroup, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int = listMovie.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listMovie[position])
    }
}