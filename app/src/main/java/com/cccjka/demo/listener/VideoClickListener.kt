package com.cccjka.demo.listener

import com.cccjka.demo.response.videobean.VideoInfoBean

interface VideoClickListener {

    fun onItemClick(bean: VideoInfoBean)
}