package com.example.morkince.movieworld.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.morkince.movieworld.R
import com.example.morkince.movieworld.models.Movie

class NowPlayingMovieAdapter(private val list: ArrayList<Movie>, private val context: Context)
    : RecyclerView.Adapter<MovieViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MovieViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.now_playing_row, parent, false)
        return MovieViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bindItem(list[position])
    }

}