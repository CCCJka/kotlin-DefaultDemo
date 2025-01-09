package com.cccjka.demo.response.videobean


import com.google.gson.annotations.SerializedName

data class Url(
    @SerializedName("name")
    val name: String?,
    @SerializedName("url")
    val url: String?
)