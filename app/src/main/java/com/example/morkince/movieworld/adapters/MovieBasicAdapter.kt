package com.example.morkince.movieworld.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.morkince.movieworld.R
import com.example.morkince.movieworld.models.Movie

class MovieBasicAdapter(private val list: ArrayList<Movie>, private val context: Context)
    : RecyclerView.Adapter<MovieBasicHolder>() {
    override fun onBindViewHolder(holder: MovieBasicHolder, position: Int) {
        holder.bindItem(list[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MovieBasicHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.branch_movie_row, parent, false)
        return MovieBasicHolder(view, context)
    }

    override fun getItemCount(): Int = list.size

}