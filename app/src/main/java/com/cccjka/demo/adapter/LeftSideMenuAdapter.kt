package com.cccjka.demo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cccjka.demo.R

class LeftSideMenuAdapter(private val name: List<String>): RecyclerView.Adapter<ViewHolder.LeftSideMenuViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder.LeftSideMenuViewHolder, position: Int) {
        holder.itemInfo.text = name[position]
        holder.itemInfo.setOnClickListener {
            listener?.onClick(name[position])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder.LeftSideMenuViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_side_menu, parent, false)
        return ViewHolder.LeftSideMenuViewHolder(view)
    }

    override fun getItemCount(): Int = name.size

    interface clickListener{
        fun onClick(clickItem: String)
    }

    var listener: clickListener ?= null

    fun setOnItemClickListener(clickListener: clickListener){
        this.listener = clickListener
    }

}