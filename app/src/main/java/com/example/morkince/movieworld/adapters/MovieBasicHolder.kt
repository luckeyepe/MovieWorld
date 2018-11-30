package com.example.morkince.movieworld.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.morkince.movieworld.R
import com.example.morkince.movieworld.models.Movie
import com.squareup.picasso.Picasso

class MovieBasicHolder(itemView: View, var context: Context): RecyclerView.ViewHolder(itemView) {
    fun bindItem(movie: Movie){
        var movieTitle: TextView = itemView.findViewById(R.id.textView_popupBranchRowMoviesTitle)
        var moviePoster: ImageView = itemView.findViewById(R.id.imageView_popupBranchRowMoviesPoster)

        Picasso.get().load(movie.movie_poster_url).into(moviePoster)
        movieTitle.text = movie.movie_title
    }
}