package com.cccjka.demo.response.videobean


import com.google.gson.annotations.SerializedName

data class Consumption(
    @SerializedName("collectionCount")
    val collectionCount: Int?,
    @SerializedName("replyCount")
    val replyCount: Int?,
    @SerializedName("shareCount")
    val shareCount: Int?
)