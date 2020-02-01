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
import com.mdudhin.moviecatalogue.adapter.ListTvShowAdapter
import com.mdudhin.moviecatalogue.detail.DetailTvShowActivity
import com.mdudhin.moviecatalogue.entity.TvShow
import kotlinx.android.synthetic.main.fragment_tv_show.*

class TvShowFragment : Fragment() {

    private lateinit var adapter: ListTvShowAdapter
    private lateinit var mainViewModel: TvShowViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tv_show, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showRecyclerList()
    }

    private fun showRecyclerList(){
        adapter = ListTvShowAdapter()
        adapter.notifyDataSetChanged()

        rv_tv_shows.layoutManager = LinearLayoutManager(context)
        rv_tv_shows.adapter = adapter

        getData()
    }

    private fun getData(){
        mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(TvShowViewModel::class.java)

        mainViewModel.setTvShow()
        rv_tv_shows.visibility = View.GONE
        showLoading(true)

        displayData()
    }

    private fun displayData(){
        mainViewModel.getTvShow().observe(viewLifecycleOwner, Observer { tvShowItems ->
            if (tvShowItems != null){
                adapter.setData(tvShowItems)
                rv_tv_shows.visibility = View.VISIBLE
                showLoading(false)

                adapter.setOnItemClickCallback(object : ListTvShowAdapter.OnItemClickCallback{
                    override fun onItemClicked(data: TvShow) {
                        showSelectedTvShow(data)
                    }
                })
            }
        })
    }

    private fun showSelectedTvShow(tvShow: TvShow) {
        val detailIntent = Intent(context, DetailTvShowActivity::class.java)
        detailIntent.putExtra(DetailTvShowActivity.EXTRA_TV_SHOW, tvShow)
        startActivity(detailIntent)
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }


}
