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
import com.example.morkince.movieworld.models.Movie
import com.example.morkince.movieworld.models.ScreeningSchedule
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.android.synthetic.main.activity_buy_ticket_branch.*
import kotlinx.android.synthetic.main.pop_up_branch_movies.view.*

class TicketBranchHolder(itemView: View, var context: Context) : RecyclerView.ViewHolder(itemView) {
    fun bindItem(branch: Branch){
        var branchName: TextView = itemView.findViewById(R.id.textView_branchRowBranchName)
        var branchLocation: TextView = itemView.findViewById(R.id.textView_branchRowBranchLocation)
        var branchCardView: CardView = itemView.findViewById(R.id.cardView_branchRowCardView)
        var movieIDS = ArrayList<String>()

        branchName.text = branch.branch_name
        branchLocation.text = branch.branch_location

        //for the movie available at that branch
        var dialogBuilder: AlertDialog.Builder?
        var dialog: AlertDialog?
        var popupView = LayoutInflater.from(context).inflate(R.layout.pop_up_branch_movies, null)
        var popupRecylerView = popupView.recylerView_popupBranchMovieRecylerView
        var movieList = ArrayList<Movie>()
        var adapter = MovieBasicAdapter(movieList, this?.context!!)


        popupRecylerView.layoutManager = LinearLayoutManager(context)
        popupRecylerView.adapter = adapter



        branchCardView.setOnClickListener {

            //garb the screening schedule that the movie has
//            FirebaseFirestore.getInstance().collection("Screening Schedule").document(movieID)
//                .get()
//                .addOnCompleteListener {
//                        task: Task<DocumentSnapshot> ->
//                    if (task.isSuccessful){
//                        var document = task.result!!.toObject(ScreeningSchedule::class.java)
//
//                        //grab the ino of the branches that show this movie
//                        for(branchID in document?.screening_schedule_branch!!){
//                            FirebaseFirestore.getInstance().collection("Branches").document(branchID)
//                                .get().addOnCompleteListener {
//                                        task: Task<DocumentSnapshot> ->
//                                    if(task.isSuccessful){
//                                        var document  = task.result!!.toObject(Branch::class.java)
//                                        branchList.add(document!!)
//                                        adapter = TicketBranchAdapter(branchList, this)
//                                        recylerView_buyTickeyBranchRecylerView.adapter = adapter
//                                    }
//                                }
//                        }
//                    }
//                }



            //popup the movies available in that branch
            var db = FirebaseFirestore.getInstance().collection("Screening Schedule")
            var query = db.whereArrayContains("screening_schedule_branch", "${branch.branch_id}")

            query.get().addOnCompleteListener {
                    task: Task<QuerySnapshot> ->
                if (task.isSuccessful){
                    var document = task.result!!.toObjects(ScreeningSchedule::class.java)

                    //popup the movies available in that branch
                    db = FirebaseFirestore.getInstance().collection("Movies")
                    for(movieId in movieIDS){
                        db.document(movieId).get().addOnCompleteListener {
                                task: Task<DocumentSnapshot> ->
                            if (task.isSuccessful){
                                var document = task.result!!.toObject(Movie::class.java)
                                //check if movie is already available
                                if (document!!.movie_is_showing == true){
                                    movieList.add(document)
                                    adapter = MovieBasicAdapter(movieList, context)
                                    popupRecylerView.adapter = adapter
                                }
                            }
                        }
                    }
                }
            }
            dialogBuilder = AlertDialog.Builder(context).setView(popupView)
            dialog = dialogBuilder!!.create()
            dialog?.show()
        }
    }
}