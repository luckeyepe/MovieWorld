package com.example.morkince.movieworld.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.example.morkince.movieworld.R
import com.example.morkince.movieworld.adapters.BranchListAdapter
import com.example.morkince.movieworld.adapters.TicketBranchAdapter
import com.example.morkince.movieworld.models.Branch
import com.example.morkince.movieworld.models.Movie
import com.example.morkince.movieworld.models.ScreeningSchedule
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.android.synthetic.main.activity_buy_ticket_branch.*
import kotlinx.android.synthetic.main.fragment_branch.view.*

class BuyTicketBranchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buy_ticket_branch)
        val movieID:String = intent.getStringExtra("movieID")
        var branchList = ArrayList<Branch>()
        var layoutManager = LinearLayoutManager(this)
        var adapter = TicketBranchAdapter(branchList, this, movieID)

        recylerView_buyTickeyBranchRecylerView.layoutManager = layoutManager
        recylerView_buyTickeyBranchRecylerView.adapter = adapter

        //change action bar title to movie title
        FirebaseFirestore.getInstance().collection("Movies").document(movieID).get().addOnCompleteListener {
            task: Task<DocumentSnapshot> ->
            if (task.isSuccessful){
                title = task.result!!.toObject(Movie::class.java)!!.movie_title
            }
        }


        //garb the screening schedule that the movie has
        FirebaseFirestore.getInstance().collection("Screening Schedule").document(movieID)
            .get()
            .addOnCompleteListener {
                task: Task<DocumentSnapshot> ->
                if (task.isSuccessful){
                    var document = task.result!!.toObject(ScreeningSchedule::class.java)

                    //grab the ino of the branches that show this movie
                    for(branchID in document?.screening_schedule_branch!!){
                        FirebaseFirestore.getInstance().collection("Branches").document(branchID)
                            .get().addOnCompleteListener {
                                task: Task<DocumentSnapshot> ->
                                if(task.isSuccessful){
                                    var document  = task.result!!.toObject(Branch::class.java)
                                    branchList.add(document!!)
                                    adapter = TicketBranchAdapter(branchList, this, movieID)
                                    recylerView_buyTickeyBranchRecylerView.adapter = adapter
                                }
                            }
                    }
                }
            }

    }
}
