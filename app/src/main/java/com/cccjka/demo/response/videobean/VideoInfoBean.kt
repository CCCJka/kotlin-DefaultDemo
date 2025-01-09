package com.cccjka.demo.response.videobean

import com.google.gson.annotations.SerializedName

data class VideoInfoBean(
    @SerializedName("name")
    val name: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("imagePath")
    val imagePath: String?,
    @SerializedName("videoPath")
    val videoPath: String?
)