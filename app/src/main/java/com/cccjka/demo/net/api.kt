package com.cccjka.demo.net

import android.database.Observable
import com.cccjka.demo.response.MediaBean
import retrofit2.http.GET
import retrofit2.http.Query

interface api {

    @GET("v2/feed?")
    fun getFirstHomeData(@Query("num") num:Int): Observable<MediaBean>

    @GET("v5/index/tab/allRec")
    fun getEditorPick(): Observable<PickBean>

}