package com.cccjka.demo.adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.cccjka.demo.R

class LeftSideMenuAdapter(private val context: Context, private val name: List<String>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var item_name: TextView

    override fun getItemCount(): Int = name.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.adapter_side_menu, parent, false)
        return view
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(getItemViewType(position)){
            holder.side_item_info.text
        }
    }

}