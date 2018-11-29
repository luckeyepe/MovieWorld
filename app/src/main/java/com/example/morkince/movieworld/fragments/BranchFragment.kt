package com.example.morkince.movieworld.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout

import com.example.morkince.movieworld.R
import com.example.morkince.movieworld.adapters.BranchListAdapter
import com.example.morkince.movieworld.models.Branch
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.android.synthetic.main.fragment_branch.*
import kotlinx.android.synthetic.main.fragment_branch.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class BranchFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_branch, container, false)

        //load up data for the recyler view
        var branchList = ArrayList<Branch>()
        var layoutManager = LinearLayoutManager(context)
        var adapter = BranchListAdapter(branchList, this!!.context!!)

        view.recylerView_branchFragmentBrachList.layoutManager = layoutManager
        view.recylerView_branchFragmentBrachList.adapter = adapter

        FirebaseFirestore.getInstance().collection("Branches")
            .get().addOnCompleteListener {
                task: Task<QuerySnapshot> ->
                if(task.isSuccessful){
                    for (document in task.result!!){
                        branchList.add(document.toObject(Branch::class.java))
                        adapter = BranchListAdapter(branchList, this?.context!!)
                        view.recylerView_branchFragmentBrachList.adapter = adapter
                    }
                }

            }




        return view
    }


}
