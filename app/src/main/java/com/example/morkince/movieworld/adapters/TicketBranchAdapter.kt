package com.example.morkince.movieworld.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.morkince.movieworld.R
import com.example.morkince.movieworld.models.Branch

class TicketBranchAdapter(private val list: ArrayList<Branch>, private val context: Context, private val movieID: String): RecyclerView.Adapter<TicketBranchHolder>()  {
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): TicketBranchHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.branch_row, parent, false)
        return TicketBranchHolder(view, context, movieID)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: TicketBranchHolder, position: Int) {
        holder.bindItem(list[position])
    }
}