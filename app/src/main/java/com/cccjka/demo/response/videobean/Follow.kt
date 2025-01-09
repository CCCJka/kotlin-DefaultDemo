package com.cccjka.demo.response.videobean


import com.google.gson.annotations.SerializedName

data class Follow(
    @SerializedName("followed")
    val followed: Boolean?,
    @SerializedName("itemId")
    val itemId: Int?,
    @SerializedName("itemType")
    val itemType: String?
)