package com.cccjka.demo.response.videobean


import com.google.gson.annotations.SerializedName

data class PlayInfo(
    @SerializedName("height")
    val height: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("type")
    val type: String?,
    @SerializedName("url")
    val url: String?,
    @SerializedName("urlList")
    val urlList: List<Url?>?,
    @SerializedName("width")
    val width: Int?
)