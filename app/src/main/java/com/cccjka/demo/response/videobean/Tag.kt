package com.cccjka.demo.response.videobean


import com.google.gson.annotations.SerializedName

data class Tag(
    @SerializedName("actionUrl")
    val actionUrl: String?,
    @SerializedName("adTrack")
    val adTrack: Any?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?
)