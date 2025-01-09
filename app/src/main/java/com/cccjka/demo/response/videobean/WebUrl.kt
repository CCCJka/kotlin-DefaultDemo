package com.cccjka.demo.response.videobean


import com.google.gson.annotations.SerializedName

data class WebUrl(
    @SerializedName("forWeibo")
    val forWeibo: String?,
    @SerializedName("raw")
    val raw: String?
)