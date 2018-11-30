package com.example.morkince.movieworld.adapters

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.net.Uri
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.morkince.movieworld.R
import com.example.morkince.movieworld.activities.BuyTicketBranchActivity
import com.example.morkince.movieworld.models.Movie
import com.squareup.picasso.Picasso
import io.opencensus.internal.StringUtil
import kotlinx.android.synthetic.main.pop_up_movie_details.view.*

class MovieViewHolder(itemView: View, var context: Context): RecyclerView.ViewHolder(itemView){
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


        //disable buy and reserve if movie is not showing
        if (movie.movie_is_showing == false){
            movieBuyNow.isEnabled = false
            movieReserveTicket.isEnabled =false
        }

        movieBuyNow.setOnClickListener {
            var dialogBuilder: AlertDialog.Builder?
            var dialog: AlertDialog?

            var popupView = LayoutInflater.from(context).inflate(R.layout.pop_up_movie_details, null)

            var popupViewPoster = popupView.imageView_popupMovieDetailMoviePoster
            var popupViewTitle = popupView.textView_popupMovieDetailMovieTitle
            var popupMovieDetail = popupView.textView_popupMovieDetailDescription
            var popupRating = popupView.textView_popupMovieDetailMovieRating
            var popupRunningTime = popupView.textView_popupMovieDetailMovieRunTime
            var popupGenre = popupView.textView_popupMovieDetailMovieGenre
            var popupTrailerVideo = popupView.button_popupMovieDetailMovieTrailer
            var popupBranch = popupView.button_popupMovieDetailMovieBranch
            var genres = ""

            //add movie poster
            Picasso.get().load(movie.movie_poster_url).into(popupViewPoster)
            //add movie details
            popupViewTitle.text = movie.movie_title
            popupRating.text = movie.movie_film_rating
            popupMovieDetail.text = movie.movie_description
            popupRunningTime.text = "Running Time: $hour hr $minutes mins"

            //get all the genres of the movie
            for(genre in movie.movie_genre!!){
                genres+=" $genre,"
            }

            //clean up string
            var cleanGenre = genres.substring(0, genres.length-1)

            popupGenre.text = "Genres:$cleanGenre"

            //play youtube video on youtube app
            popupTrailerVideo.setOnClickListener {
                var intent = Intent(Intent.ACTION_VIEW, Uri.parse(movie.movie_trailer_url))
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                intent.putExtra("force_fullscreen",true)//set youtube to full screen
                intent.setPackage("com.google.android.youtube")
                startActivity(context, intent, null)
            }

            popupBranch.setOnClickListener {
                var intent = Intent(context, BuyTicketBranchActivity::class.java)
                intent.putExtra("movieID", movie.movie_id)
                startActivity(context, intent, null)
            }


            dialogBuilder = AlertDialog.Builder(context).setView(popupView)
            dialog = dialogBuilder!!.create()
            dialog?.show()
        }

        movieReserveTicket.setOnClickListener {

        }
    }
}