package com.cccjka.demo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cccjka.demo.R
import com.cccjka.demo.db.bean.HistoryBean
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class FragmentHistoryAdapter(val data: List<HistoryBean>): RecyclerView.Adapter<ViewHolder.HistoryFragmentViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder.HistoryFragmentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_history_fragment, parent, false)
        return ViewHolder.HistoryFragmentViewHolder(view)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder.HistoryFragmentViewHolder, position: Int) {
        val context = holder.itemView.context
        val info = data[position]
        val instant = info.time?.toLong()?.let { Instant.ofEpochSecond(it) }
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.systemDefault())  // 使用系统默认时区
        val time = formatter.format(instant)
        Glide.with(context).load(info.imgUrl).into(holder.img)
        holder.name.text = info.name
        holder.description.text = info.description
        holder.time.text = time
    }
}