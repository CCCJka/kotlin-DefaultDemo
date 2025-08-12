package com.cccjka.demo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cccjka.demo.R
import com.cccjka.demo.application.MyApplication
import com.cccjka.demo.db.DbHelper
import com.cccjka.demo.db.bean.HistoryBean
import com.cccjka.demo.listener.VideoClickListener
import com.cccjka.demo.response.videobean.VideoInfoBean
import com.cccjka.demo.utils.CommonUtils
import java.time.Instant

class FragmentVideoAdapter(val data: List<String>): RecyclerView.Adapter<ViewHolder.MainFragmentViewHolder>() {

    private var listener: VideoClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder.MainFragmentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_video_fragment, parent, false)
        return ViewHolder.MainFragmentViewHolder(view)
    }

    override fun getItemCount(): Int = data.size

    fun setOnClickListener(listener: VideoClickListener){
        this.listener = listener
    }

    override fun onBindViewHolder(holder: ViewHolder.MainFragmentViewHolder, position: Int) {
        val context = holder.itemView.context
        val info = data[position]
        val videoInfoBean = CommonUtils.fromJson(info, VideoInfoBean::class.java)
        videoInfoBean.let {
            if ((it.description != null && it.description != "") && (it.name != null && it.name != "")) {
                Glide.with(context).load(videoInfoBean.imagePath).into(holder.videoPic)
                holder.title.text = it.name
                val des = it.description.split("From")
                if (des.size >= 2){
                    des[1].let {
                        holder.auth.text = des[1]
                    }
                    des[0].let {
                        holder.description.text = des[0]
                    }
                } else {
                    holder.description.text = it.description
                }
                holder.videoPic.setOnClickListener{
                    listener?.onItemClick(videoInfoBean)
                    saveHistory(position)
                }
            }
        }
    }

    private fun saveHistory(position: Int) {
        val convertInfo = CommonUtils.fromJson(data[position], VideoInfoBean::class.java)
        val currentTime = Instant.now().epochSecond.toInt()
        val historyBean = HistoryBean(convertInfo.name, convertInfo.videoPath, convertInfo.imagePath, convertInfo.description, currentTime)
        DbHelper.get().saveHistory(historyBean)
    }

    override fun onViewRecycled(holder: ViewHolder.MainFragmentViewHolder) {
        super.onViewRecycled(holder)
        val img = holder.videoPic
        img.let { Glide.with(MyApplication.context).clear(it) }
    }

    fun onDestroy(){
        listener = null
    }

}