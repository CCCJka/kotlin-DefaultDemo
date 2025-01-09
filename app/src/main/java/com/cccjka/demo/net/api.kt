package com.cccjka.demo.net


import com.cccjka.demo.response.RequestVideoBean
import io.reactivex.Observable
import retrofit2.http.GET

interface api {

    //首页
    @GET("v4/tabs/selected")
    fun getSelectInfo(): Observable<RequestVideoBean>

    @GET("v4/discovery/hot")
    fun getHotPoiont(): Observable<RequestVideoBean>

}