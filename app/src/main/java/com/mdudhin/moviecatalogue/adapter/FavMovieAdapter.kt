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

class FavMovieAdapter(private val activity: Activity?) : RecyclerView.Adapter<FavMovieAdapter.FavMovieViewHolder>() {

    private val posterSize = "w185"

    var listFavMovies = ArrayList<Movie>()
        set(listFavMovies) {
            if (listFavMovies.size > -1){
                this.listFavMovies.clear()
            }
            this.listFavMovies.addAll(listFavMovies)

            notifyDataSetChanged()
        }

    inner class FavMovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavMovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_row_movies, parent, false)
        return FavMovieViewHolder(view)
    }

    override fun getItemCount(): Int = this.listFavMovies.size

    override fun onBindViewHolder(holder: FavMovieViewHolder, position: Int) {
        holder.bind(listFavMovies[position])
    }
}