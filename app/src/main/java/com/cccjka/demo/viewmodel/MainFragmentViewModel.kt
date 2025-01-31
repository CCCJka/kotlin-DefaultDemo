package com.cccjka.demo.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.cccjka.demo.navigator.RequestPageNavigator
import com.cccjka.demo.net.OkhttpClient
import com.cccjka.demo.response.videobean.Item
import com.cccjka.demo.response.videobean.VideoInfoBean
import com.cccjka.demo.utils.CommonUtils
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class MainFragmentViewModel(val navigator: RequestPageNavigator): ViewModel() {

    fun requestPageInfo(){
        val dispose = OkhttpClient.service.getSelectInfo()
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.single())
            .subscribeBy(
                onComplete = {
                },
                onNext = {
                    it?.let { it ->
                        val itemList: List<Item>? = it.itemList
                        val list: ArrayList<String> = arrayListOf()
                        itemList?.let { itemInfo ->
                            addDataInfoToList(itemInfo, list)
                            navigator.loadPageInfo(list)
                        }
                    }
                },
                onError = {
                    Log.e("", "", it)
                }
            )
    }

    private fun addDataInfoToList(list: List<Item>, arrayList: ArrayList<String>){
        for (data in list){
            if (data.type == "video"){
                val videoInfoBean = VideoInfoBean(data.data?.title, data.data?.description, data.data?.cover?.feed, data.data?.playUrl)
                val jsonData = CommonUtils.toJson(videoInfoBean)
                arrayList.add(jsonData)
            }
        }
    }

    fun onDestroy(){

    }
}
