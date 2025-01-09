package com.cccjka.demo.response.videobean


import com.google.gson.annotations.SerializedName

data class Item(
    @SerializedName("data")
    val `data`: Data?,
    @SerializedName("type")
    val type: String?,
    @SerializedName("trackingData")
    val trackingData: String?,
    @SerializedName("tag")
    val tag: String?,
    @SerializedName("id")
    val id: Long?,
    @SerializedName("adIndex")
    val adIndex: Long?
)