package com.mdudhin.moviecatalogue.adapter

import androidx.annotation.Nullable
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.mdudhin.moviecatalogue.MovieFragment
import com.mdudhin.moviecatalogue.R
import com.mdudhin.moviecatalogue.TvShowFragment
import com.mdudhin.moviecatalogue.ui.home.HomeFragment

class SectionsPagerAdapter(private val mContext: HomeFragment, fm: FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    @StringRes
    private val TAB_TITLES = intArrayOf(
        R.string.tab_movie,
        R.string.tab_tv_show
    )

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = MovieFragment()
            1-> fragment = TvShowFragment()
        }
        return fragment as Fragment
    }

    @Nullable
    override fun getPageTitle(position: Int): CharSequence? {
        return mContext.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        return 2
    }
}