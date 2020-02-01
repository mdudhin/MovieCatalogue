package com.mdudhin.moviecatalogue.ui.favorite


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mdudhin.moviecatalogue.R
import com.mdudhin.moviecatalogue.adapter.FavSectionsPagerAdapter
import kotlinx.android.synthetic.main.fragment_favorite.*

class FavoriteFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val favSectionsPagerAdapter =
            FavSectionsPagerAdapter(
                this,
                childFragmentManager
            )
        view_pager.adapter = favSectionsPagerAdapter
        tabs.setupWithViewPager(view_pager)
    }


}
