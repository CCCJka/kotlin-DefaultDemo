package com.cccjka.demo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cccjka.demo.R

class LeftSideMenuAdapter(private val name: List<String>): RecyclerView.Adapter<LeftSideMenuAdapter.LeftSideMenuViewHolder>() {

    override fun onBindViewHolder(holder: LeftSideMenuViewHolder, position: Int) {
        holder.itemInfo.text = name[position]
        holder.itemInfo.setOnClickListener {
            listener?.onClick(name[position])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeftSideMenuViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_side_menu, parent, false)
        return LeftSideMenuViewHolder(view)
    }

    override fun getItemCount(): Int = name.size

    interface clickListener{
        fun onClick(clickItem: String)
    }

    var listener: clickListener ?= null

    fun setOnItemClickListener(clickListener: clickListener){
        this.listener = clickListener
    }

    inner class LeftSideMenuViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var itemInfo: TextView = view.findViewById(R.id.side_item_info)
    }

}