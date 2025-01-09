package com.cccjka.demo.response.videobean


import com.google.gson.annotations.SerializedName

data class Author(
    @SerializedName("adTrack")
    val adTrack: Any?,
    @SerializedName("authorType")
    val authorType: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("follow")
    val follow: Follow?,
    @SerializedName("icon")
    val icon: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("latestReleaseTime")
    val latestReleaseTime: Long?,
    @SerializedName("link")
    val link: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("videoNum")
    val videoNum: Int?
)