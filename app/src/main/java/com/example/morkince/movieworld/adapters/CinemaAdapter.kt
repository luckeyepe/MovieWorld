package com.example.morkince.movieworld.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.morkince.movieworld.R
import com.example.morkince.movieworld.models.Cinema

class CinemaAdapter(private val list: ArrayList<Cinema>, private val context: Context): RecyclerView.Adapter<CinemaHolder>()  {
    override fun onBindViewHolder(holder: CinemaHolder, position: Int) {
        holder.bindItem(list[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): CinemaHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.cinema_row, parent, false)
        return CinemaHolder(view, context)
    }

    override fun getItemCount(): Int = list.size

}