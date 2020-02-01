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
import com.mdudhin.moviecatalogue.detail.DetailTvShowActivity
import com.mdudhin.moviecatalogue.entity.TvShow
import kotlinx.android.synthetic.main.item_row_tv_shows.view.*

class FavTvShowAdapter(private val activity: Activity?) : RecyclerView.Adapter<FavTvShowAdapter.FavTvShowViewHolder>() {

    private val posterSize = "w185"

    var listFavTvShow = ArrayList<TvShow>()
        set(listFavTvShow) {
            if (listFavTvShow.size > -1){
                this.listFavTvShow.clear()
            }
            this.listFavTvShow.addAll(listFavTvShow)

            notifyDataSetChanged()
        }

    inner class FavTvShowViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(tvShow: TvShow){

            val rating = tvShow.vote_average / 2

            with(itemView){
                Glide.with(itemView.context)
                    .load("https://image.tmdb.org/t/p/$posterSize/${tvShow.poster_path}")
                    .apply(RequestOptions().override(100,120))
                    .into(poster_tv_show)

                tv_show_name.text = tvShow.name
                tv_first_air_date.text = tvShow.first_air_date
                tv_tv_show_overview.text = tvShow.overview
                rb_vote_average.rating = rating.toFloat()

                cv_item_tv_show.setOnClickListener(CustomOnItemClickListener(adapterPosition, object : CustomOnItemClickListener.OnItemClickCallback {
                    override fun onItemClicked(view: View, position: Int) {
                        val intent = Intent(activity, DetailTvShowActivity::class.java)
                        intent.putExtra(DetailTvShowActivity.EXTRA_POSITION, position)
                        intent.putExtra(DetailTvShowActivity.EXTRA_TV_SHOW, tvShow)
                        activity?.startActivity(intent)
                    }
                }))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavTvShowViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_row_tv_shows, parent, false)
        return FavTvShowViewHolder(view)
    }

    override fun getItemCount(): Int = this.listFavTvShow.size

    override fun onBindViewHolder(holder: FavTvShowViewHolder, position: Int) {
        holder.bind(listFavTvShow[position])
    }
}