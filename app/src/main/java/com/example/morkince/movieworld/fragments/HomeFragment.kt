package com.example.morkince.movieworld.fragments


import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.morkince.movieworld.R
import com.example.morkince.movieworld.adapters.HomeFragmentSectionAdapter
import kotlinx.android.synthetic.main.fragment_home.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        var homeFragmentSectionAdapter = HomeFragmentSectionAdapter(fragmentManager!!)

        view.viewPager_homeFragmentViewPager.adapter = homeFragmentSectionAdapter
        view.tabLayout_homeFragmentTabLayout.setupWithViewPager(view.viewPager_homeFragmentViewPager)
        view.tabLayout_homeFragmentTabLayout.setTabTextColors(Color.WHITE, Color.MAGENTA)//change the color of the text when selecting

        return view
    }


}
