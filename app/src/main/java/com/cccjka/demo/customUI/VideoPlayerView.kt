package com.cccjka.demo.customUI

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cccjka.demo.R
import com.cccjka.demo.response.videobean.VideoInfoBean
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer
import com.shuyu.gsyvideoplayer.video.base.GSYVideoView.CURRENT_STATE_PAUSE

class VideoPlayerView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    private var videoContainer: RelativeLayout? = null
    private var player: StandardGSYVideoPlayer? = null
    private var videoName: TextView? = null
    private var authName: TextView? = null
    private var videoDescription: TextView? = null
    private var rvLoadMore: RecyclerView? = null

    override fun onFinishInflate() {
        init()
        super.onFinishInflate()
    }

    private fun init(){
        val view = LayoutInflater.from(context).inflate(R.layout.video_player_view, this, true)
        videoContainer = view.findViewById(R.id.videoContainer)
        videoName = view.findViewById(R.id.tv_video_name)
        authName = view.findViewById(R.id.tv_auth_name)
        videoDescription = view.findViewById(R.id.tv_video_description)
        rvLoadMore = view.findViewById(R.id.rv_load_more)
    }

    fun setData(bean: VideoInfoBean){
        player = StandardGSYVideoPlayer(context)
        player?.setUp(bean.videoPath, true, "")
        player?.startPlayLogic()
        player?.backButton?.setOnClickListener{
            setVisiblyGone()
            release()
        }
        videoName?.text = bean.name
        val des = bean.description?.split("From")
        if (des?.size!! > 2){
            des[1].let { authName?.text = des[1] }
            des[0].let { videoDescription?.text = des[0] }
        } else {
            des[0].let { videoDescription?.text = bean.description }
        }
        videoContainer?.addView(player)
    }

    fun pauseVideo(){
        player?.let {
            if (it.isInPlayingState){
                it.onVideoPause()
            }
        }
    }

    fun resumeVideo(){
        player?.let{
            if (it.currentState == CURRENT_STATE_PAUSE){
                it.onVideoResume()
            }
        }
    }

    fun currentState() = player?.currentState

    fun setVisiblyGone() {
        if (visibility == VISIBLE || visibility == INVISIBLE){
            visibility = GONE
        }
    }

    fun release(){
        videoContainer?.removeAllViews()
        player?.onVideoPause()
        player?.release()
    }

}