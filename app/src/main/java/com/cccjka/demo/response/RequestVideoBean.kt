package com.cccjka.demo.response


import com.cccjka.demo.response.videobean.Item
import com.google.gson.annotations.SerializedName

data class RequestVideoBean(
    @SerializedName("count")
    val count: Int?,
    @SerializedName("date")
    val date: Long?,
    @SerializedName("dialog")
    val dialog: Any?,
    @SerializedName("itemList")
    val itemList: List<Item>?,
    @SerializedName("nextPageUrl")
    val nextPageUrl: String?,
    @SerializedName("nextPublishTime")
    val nextPublishTime: Long?,
    @SerializedName("total")
    val total: Int?
)