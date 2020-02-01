package com.mdudhin.moviecatalogue.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mdudhin.moviecatalogue.R
import com.mdudhin.moviecatalogue.entity.TvShow
import kotlinx.android.synthetic.main.item_row_tv_shows.view.*

class ListTvShowAdapter : RecyclerView.Adapter<ListTvShowAdapter.ListViewHolder>() {

    private val listTvShow = ArrayList<TvShow>()
    private val posterSize = "w185"

    fun setData(items: ArrayList<TvShow>){
        listTvShow.clear()
        listTvShow.addAll(items)
        notifyDataSetChanged()
    }

    private var onItemClickCallback: OnItemClickCallback? = null

    interface OnItemClickCallback {
        fun onItemClicked(data: TvShow)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(tvShow: TvShow){

            val rating = tvShow.vote_average / 2

            with(itemView){
                Glide.with(context)
                    .load("https://image.tmdb.org/t/p/$posterSize/${tvShow.poster_path}")
                    .apply(RequestOptions().override(100,120))
                    .into(poster_tv_show)

                tv_show_name.text = tvShow.name
                tv_first_air_date.text = tvShow.first_air_date
                rb_vote_average.rating = rating.toFloat()
                tv_tv_show_overview.text = tvShow.overview

                itemView.setOnClickListener{onItemClickCallback?.onItemClicked(tvShow)}
            }

        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): ListViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_row_tv_shows, viewGroup, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int = listTvShow.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listTvShow[position])
    }
}