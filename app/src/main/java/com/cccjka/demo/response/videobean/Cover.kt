package com.cccjka.demo.response.videobean


import com.google.gson.annotations.SerializedName

data class Cover(
    @SerializedName("blurred")
    val blurred: String?,
    @SerializedName("detail")
    val detail: String?,
    @SerializedName("feed")
    val feed: String?,
    @SerializedName("sharing")
    val sharing: Any?
)