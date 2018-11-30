package com.example.morkince.movieworld.adapters

import android.content.Context
import android.support.v7.app.AlertDialog
import android.support.v7.widget.CardView
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.example.morkince.movieworld.R
import com.example.morkince.movieworld.models.Branch
import com.example.morkince.movieworld.models.Cinema
import com.example.morkince.movieworld.models.Movie
import com.example.morkince.movieworld.models.ScreeningSchedule
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.android.synthetic.main.activity_buy_ticket_branch.*
import kotlinx.android.synthetic.main.pop_up_branch_cinema.view.*
import kotlinx.android.synthetic.main.pop_up_branch_movies.view.*

class TicketBranchHolder(itemView: View, var context: Context, var movieID: String) : RecyclerView.ViewHolder(itemView) {
    fun bindItem(branch: Branch){
        var branchName: TextView = itemView.findViewById(R.id.textView_branchRowBranchName)
        var branchLocation: TextView = itemView.findViewById(R.id.textView_branchRowBranchLocation)
        var branchCardView: CardView = itemView.findViewById(R.id.cardView_branchRowCardView)
        var movieIDS = ArrayList<String>()
        var cinemaList = ArrayList<Cinema>()

        branchName.text = branch.branch_name
        branchLocation.text = branch.branch_location

        //for the cinemas available at that branch
        var dialogBuilder: AlertDialog.Builder?
        var dialog: AlertDialog?
        var popupView = LayoutInflater.from(context).inflate(R.layout.pop_up_branch_cinema, null)

        var popupRecyclerView = popupView.recyclerView_popupBranchCinemaRecylerView
        var adapter = CinemaAdapter(cinemaList, context)


        popupRecyclerView.layoutManager = LinearLayoutManager(context)
        popupRecyclerView.adapter = adapter



        branchCardView.setOnClickListener {

            //garb the screening schedule that the movie has
            FirebaseFirestore.getInstance().collection("Screening Schedule").document(movieID)
                .get()
                .addOnCompleteListener {
                        task: Task<DocumentSnapshot> ->
                    if (task.isSuccessful){
                        var document = task.result!!.toObject(ScreeningSchedule::class.java)

                        //grab the cinema numeber for that branch
                        for(cinemaID in document?.screening_schedule_cinema_id!!){
                            var cinema = Cinema()
                            cinema.cinema_id = cinemaID.key
                            cinema.cinema_name = cinemaID.value
                            cinema.cinema_showing_time = document.screening_schedule_date
                            cinemaList.add(cinema)
                        }

                        adapter = CinemaAdapter(cinemaList, context)
                        popupRecyclerView.adapter = adapter
                    }
                }

            dialogBuilder = AlertDialog.Builder(context).setView(popupView)
            dialog = dialogBuilder!!.create()
            dialog?.show()
        }
    }
}