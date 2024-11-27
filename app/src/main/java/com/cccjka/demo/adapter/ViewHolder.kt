package com.cccjka.demo.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cccjka.demo.R

class ViewHolder {

    class MainFragmentViewHolder(view: View): RecyclerView.ViewHolder(view){
        var title = view.findViewById<TextView>(R.id.tv_fragment_title)
        var content = view.findViewById<TextView>(R.id.tv_fragment_content)
        var img1 = view.findViewById<ImageView>(R.id.iv_page_img1)
        var img2 = view.findViewById<ImageView>(R.id.iv_page_img2)
        var img3 = view.findViewById<ImageView>(R.id.iv_page_img3)
    }

    class LeftSideMenuViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var itemInfo: TextView = view.findViewById(R.id.side_item_info)
    }
}