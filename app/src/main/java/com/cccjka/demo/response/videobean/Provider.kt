package com.cccjka.demo.response.videobean


import com.google.gson.annotations.SerializedName

data class Provider(
    @SerializedName("alias")
    val alias: String?,
    @SerializedName("icon")
    val icon: String?,
    @SerializedName("name")
    val name: String?
)