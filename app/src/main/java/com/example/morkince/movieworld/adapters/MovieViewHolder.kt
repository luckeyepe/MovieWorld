package com.example.morkince.movieworld.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.morkince.movieworld.R
import com.example.morkince.movieworld.models.Movie
import com.squareup.picasso.Picasso

class MovieViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
    fun bindItem(movie: Movie){
        var movieTitle: TextView = itemView.findViewById(R.id.textView_nowPlayingRowMovieTitle)
        var movieRunTime: TextView = itemView.findViewById(R.id.textView_nowPlayingRowMovieRunTime)
        var movieBuyNow: Button = itemView.findViewById(R.id.button_nowPlayingRowBuyTicket)
        var movieReserveTicket: Button = itemView.findViewById(R.id.button_nowPlayingRowReserveTicket)
        var moviePoster: ImageView = itemView.findViewById(R.id.imageView_nowPlayingRowMoviePoster)


        Picasso.get().load(movie.movie_poster_url).into(moviePoster)

        movieTitle.text = movie.movie_title
        var hour = movie.movie_run_time!! / 60
        var minutes = movie.movie_run_time!! % 60
        movieRunTime.text = "$hour HR $minutes MINS"

        movieBuyNow.setOnClickListener {
            //todo  popup fragment with movie details
        }

        movieReserveTicket.setOnClickListener {
            //todo  popup fragment with movie details
        }
    }
}