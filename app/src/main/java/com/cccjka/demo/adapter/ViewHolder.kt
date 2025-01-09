package com.cccjka.demo.adapter

import android.view.View
import android.webkit.WebView
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cccjka.demo.R
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer

class ViewHolder {

    class MainFragmentViewHolder(view: View): RecyclerView.ViewHolder(view){
        var gsyPlayer = view.findViewById<RelativeLayout>(R.id.gsy_player)
        var title = view.findViewById<TextView>(R.id.tv_fragment_title)
        var description = view.findViewById<TextView>(R.id.tv_fragment_description)
    }

    class LeftSideMenuViewHolder(view: View): RecyclerView.ViewHolder(view) {
        var itemInfo: TextView = view.findViewById(R.id.side_item_info)
    }

    class HistoryFragmentViewHolder(view: View): RecyclerView.ViewHolder(view){
        var name = view.findViewById<TextView>(R.id.tv_history_name)
        var img = view.findViewById<ImageView>(R.id.iv_history_img)
        var description = view.findViewById<TextView>(R.id.tv_history_description)
        var time = view.findViewById<TextView>(R.id.tv_history_time)
    }
}