package com.cccjka.demo.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cccjka.demo.R
import com.cccjka.demo.db.DbHelper
import com.cccjka.demo.db.bean.HistoryBean
import com.cccjka.demo.response.videobean.VideoInfoBean
import com.cccjka.demo.utils.CommonUtils
import com.shuyu.gsyvideoplayer.listener.GSYStateUiListener
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer
import java.time.Instant
import java.util.Calendar

class FragmentVideoAdapter(private val data: List<String>): RecyclerView.Adapter<ViewHolder.MainFragmentViewHolder>() {

    private lateinit var player: StandardGSYVideoPlayer

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder.MainFragmentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_video_fragment, parent, false)
        return ViewHolder.MainFragmentViewHolder(view)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder.MainFragmentViewHolder, position: Int) {
        val context = holder.itemView.context
        val info = data[position]
        val videoInfoBean = CommonUtils.fromJson(info, VideoInfoBean::class.java)
        player = StandardGSYVideoPlayer(context)
        videoInfoBean.let {
            if ((it.description != null && it.description != "") && (it.name != null && it.name != "")) {
                val url = videoInfoBean.videoPath ?: ""
                player.setUp(url, false, "")
                val img = ImageView(context)
                Glide.with(context).load(videoInfoBean.imagePath).into(img)
                player.thumbImageView = img
                holder.title.text = it.name
                holder.description.text = it.description
                holder.gsyPlayer.addView(player)
            }
        }
        player.gsyStateUiListener = GSYStateUiListener{
            when(it){
                GSYVideoPlayer.CURRENT_STATE_PLAYING -> saveHistory(position)
            }
        }
    }

    private fun saveHistory(position: Int) {
        val convertInfo = CommonUtils.fromJson(data[position], VideoInfoBean::class.java)
        val currentTime = Instant.now().epochSecond.toInt()
        val historyBean = HistoryBean(convertInfo.name, convertInfo.videoPath, convertInfo.imagePath, convertInfo.description, currentTime)
        DbHelper.get().saveHistory(historyBean)
    }

    fun pauseVideo(){
        player.onVideoPause()
    }

    override fun onViewRecycled(holder: ViewHolder.MainFragmentViewHolder) {
        holder.gsyPlayer.removeAllViews()
        holder.gsyPlayer = null
        player.release()
        holder.title = null
        holder.description = null
        super.onViewRecycled(holder)
    }

    override fun onViewDetachedFromWindow(holder: ViewHolder.MainFragmentViewHolder) {
        super.onViewDetachedFromWindow(holder)
        val img = player.thumbImageView
        Glide.with(holder.itemView.context).clear(img)
    }

    fun onDestroy(){
        player.release()
    }

}