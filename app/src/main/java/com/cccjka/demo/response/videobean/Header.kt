package com.cccjka.demo.response.videobean


import com.google.gson.annotations.SerializedName

data class Header(
    @SerializedName("actionUrl")
    val actionUrl: String?,
    @SerializedName("cover")
    val cover: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("font")
    val font: String?,
    @SerializedName("iconList")
    val iconList: List<String?>?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("label")
    val label: Label?,
    @SerializedName("title")
    val title: String?
)