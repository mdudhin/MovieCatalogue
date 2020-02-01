package com.mdudhin.moviecatalogue.ui.home

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment

import com.mdudhin.moviecatalogue.R
import com.mdudhin.moviecatalogue.adapter.SectionsPagerAdapter
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sectionsPagerAdapter =
            SectionsPagerAdapter(
                this,
                childFragmentManager
            )
        view_pager.adapter = sectionsPagerAdapter
        tabs.setupWithViewPager(view_pager)

    }

}
