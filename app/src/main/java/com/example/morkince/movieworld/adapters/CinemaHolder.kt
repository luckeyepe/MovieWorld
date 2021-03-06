package com.example.morkince.movieworld.adapters

import android.content.Context
import android.content.Intent
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.morkince.movieworld.R
import com.example.morkince.movieworld.activities.BuyTicketSeatActivity
import com.example.morkince.movieworld.models.Branch
import com.example.morkince.movieworld.models.Cinema
import java.text.SimpleDateFormat

class CinemaHolder(itemView: View, var context: Context) : RecyclerView.ViewHolder(itemView)  {
    fun bindItem(cinema: Cinema){
        var cinemaName: TextView = itemView.findViewById(R.id.textView_cinemaRowCinemaName)
        var cinemaSchedule: TextView = itemView.findViewById(R.id.textView_cinemaRowShowingTime)
        var cinemaCardView: CardView = itemView.findViewById(R.id.cardView_cinemaRowCardView)

        cinemaName.text = cinema.cinema_name
        cinemaSchedule.text = cinema.cinema_showing_time!!.toDate().toString()

        cinemaCardView.setOnClickListener {
            var intent = Intent(context, BuyTicketSeatActivity::class.java)
            intent.putExtra("cinemaName", cinema.cinema_name)

            context.startActivity(intent)
        }
    }

}