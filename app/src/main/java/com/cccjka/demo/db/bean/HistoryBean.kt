package com.cccjka.demo.db.bean

import com.google.gson.annotations.SerializedName

data class HistoryBean(
    @SerializedName("name")
    var name: String? = "",
    @SerializedName("url")
    var url: String? = "",
    @SerializedName("imgUrl")
    var imgUrl: String? = "",
    @SerializedName("description")
    var description: String? = "",
    @SerializedName("time")
    var time: Int? = 0
)