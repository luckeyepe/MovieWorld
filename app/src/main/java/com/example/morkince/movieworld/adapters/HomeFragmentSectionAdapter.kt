package com.example.morkince.movieworld.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.example.morkince.movieworld.fragments.AdvanceTicketFragment
import com.example.morkince.movieworld.fragments.ComingSoonFragment
import com.example.morkince.movieworld.fragments.NowPlayingFragment

class HomeFragmentSectionAdapter(fm: FragmentManager): FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        when(position){
            0 -> return NowPlayingFragment()
            1 -> return ComingSoonFragment()
            2 -> return AdvanceTicketFragment()
        }
        return null!!
    }

    override fun getCount(): Int {
        return 3//because of 3 tabs
    }
}