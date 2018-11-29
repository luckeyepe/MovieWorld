package com.example.morkince.movieworld.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.morkince.movieworld.R
import com.example.morkince.movieworld.adapters.NowPlayingMovieAdapter
import com.example.morkince.movieworld.models.Movie
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.android.synthetic.main.fragment_now_playing.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
/**
 * A simple [Fragment] subclass.
 *
 */
class NowPlayingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_now_playing, container, false)
        var db = FirebaseFirestore.getInstance().collection("Movies")
        var query = db.whereEqualTo("movie_is_showing",true)
        var movieList = ArrayList<Movie>()
        var adapter = NowPlayingMovieAdapter(movieList, this?.context!!)


        view.recyclerView_nowPlayingFragmentRecyclerView.layoutManager = LinearLayoutManager(context)
        view.recyclerView_nowPlayingFragmentRecyclerView.adapter = adapter

        query.get().addOnCompleteListener {
            task: Task<QuerySnapshot> ->
            run {
                if (task.isSuccessful) {
                    for (document in task.result!!) {
                        document.id
                        movieList.add(document.toObject(Movie::class.java))
                        adapter = NowPlayingMovieAdapter(movieList, this?.context!!)
                        view.recyclerView_nowPlayingFragmentRecyclerView.adapter = adapter
                    }
                }
            }
        }

        return view
    }


}
