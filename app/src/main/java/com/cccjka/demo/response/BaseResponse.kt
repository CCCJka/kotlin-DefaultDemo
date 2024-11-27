package com.cccjka.demo.response


import com.google.gson.annotations.SerializedName

data class BaseResponse(
    @SerializedName("code")
    val code: Int?,
    @SerializedName("data")
    val `data`: String?,
    @SerializedName("msg")
    val msg: String?
)