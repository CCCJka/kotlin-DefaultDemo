package com.cccjka.demo.response


import com.google.gson.annotations.SerializedName

data class NewContentResponse(
    @SerializedName("content")
    val content: String?,
    @SerializedName("img")
    val img: Int?,
    @SerializedName("title")
    val title: String?
)