package com.example.morkince.movieworld.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.example.morkince.movieworld.R
import com.example.morkince.movieworld.models.Branch

class BranchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bindItem(branch: Branch){
        var branchName: TextView = itemView.findViewById(R.id.textView_branchRowBranchName)
        var branchLocation: TextView = itemView.findViewById(R.id.textView_branchRowBranchLocation)

        branchName.text = branch.branch_name
        branchLocation.text = branch.branch_location
    }

}
