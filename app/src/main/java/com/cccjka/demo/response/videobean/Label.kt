package com.cccjka.demo.response.videobean


import com.google.gson.annotations.SerializedName

data class Label(
    @SerializedName("card")
    val card: Any?,
    @SerializedName("detail")
    val detail: Any?,
    @SerializedName("text")
    val text: Any?
)