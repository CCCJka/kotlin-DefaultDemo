package com.cccjka.demo.response.videobean


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("actionUrl")
    val actionUrl: String?,
    @SerializedName("adTrack")
    val adTrack: Any?,
    @SerializedName("author")
    val author: Any?,
    @SerializedName("campaign")
    val campaign: Any?,
    @SerializedName("category")
    val category: String?,
    @SerializedName("collected")
    val collected: Boolean?,
    @SerializedName("consumption")
    val consumption: Consumption?,
    @SerializedName("count")
    val count: Int?,
    @SerializedName("cover")
    val cover: Cover?,
    @SerializedName("dataType")
    val dataType: String?,
    @SerializedName("date")
    val date: Long?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("duration")
    val duration: Int?,
    @SerializedName("favoriteAdTrack")
    val favoriteAdTrack: Any?,
    @SerializedName("font")
    val font: String?,
    @SerializedName("header")
    val header: Header?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("idx")
    val idx: Int?,
    @SerializedName("itemList")
    val itemList: List<Item>?,
    @SerializedName("label")
    val label: Any?,
    @SerializedName("playInfo")
    val playInfo: List<PlayInfo>?,
    @SerializedName("playUrl")
    val playUrl: String?,
    @SerializedName("played")
    val played: Boolean?,
    @SerializedName("promotion")
    val promotion: Any?,
    @SerializedName("provider")
    val provider: Provider?,
    @SerializedName("releaseTime")
    val releaseTime: Long?,
    @SerializedName("shareAdTrack")
    val shareAdTrack: Any?,
    @SerializedName("tags")
    val tags: List<Tag>?,
    @SerializedName("text")
    val text: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("type")
    val type: String?,
    @SerializedName("waterMarks")
    val waterMarks: Any?,
    @SerializedName("webAdTrack")
    val webAdTrack: Any?,
    @SerializedName("webUrl")
    val webUrl: WebUrl?
)