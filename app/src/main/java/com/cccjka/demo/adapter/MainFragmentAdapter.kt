package com.cccjka.demo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cccjka.demo.R
import com.cccjka.demo.response.NewContentResponse
import com.cccjka.demo.utils.CommonUtils

class MainFragmentAdapter(private val data: List<String>): RecyclerView.Adapter<ViewHolder.MainFragmentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder.MainFragmentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_main_fragment, parent, false)
        return ViewHolder.MainFragmentViewHolder(view)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder.MainFragmentViewHolder, position: Int) {
        for (info in data){
            val newContent = CommonUtils.fromJson(info, NewContentResponse::class.java)
            newContent.let {
                holder.title.text = newContent.title ?: "Error"
                holder.content.text = newContent.content ?: "Error"
                holder.img1.setImageResource(R.drawable.up)
                holder.img2.setImageResource(R.drawable.up)
                holder.img3.setImageResource(R.drawable.up)
//                newContent.img.let {
//                    val id = holder.itemView.resources.getIdentifier(newContent.img, null, null)
//                    holder.img.setImageResource(id ?: R.drawable.user)
//                }
            }
        }
    }

}