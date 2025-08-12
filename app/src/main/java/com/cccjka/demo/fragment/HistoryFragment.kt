package com.cccjka.demo.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.cccjka.demo.adapter.FragmentHistoryAdapter
import com.cccjka.demo.databinding.FragmentHistoryBinding
import com.cccjka.demo.db.bean.HistoryBean
import com.cccjka.demo.listener.VideoClickListener
import com.cccjka.demo.response.videobean.VideoInfoBean
import com.cccjka.demo.viewmodel.HistoryFragmentViewModel
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer

class HistoryFragment: Fragment(){

    private lateinit var viewBinding: FragmentHistoryBinding
    private var viewModel: HistoryFragmentViewModel? = null
    private var adapter: FragmentHistoryAdapter? = null
    private var historyList: ArrayList<StandardGSYVideoPlayer> = arrayListOf()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewBinding = FragmentHistoryBinding.inflate(layoutInflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    fun init(){
        viewModel = HistoryFragmentViewModel()
        adapter = viewModel?.loadHistoryPage()?.let { FragmentHistoryAdapter(it) }
        val linearLayout = LinearLayoutManager(context)
        viewBinding.rvHistory.layoutManager = linearLayout
        viewBinding.rvHistory.adapter = adapter
        viewBinding.rvHistory.setItemViewCacheSize(100)
        adapter?.setOnClickListener(object : VideoClickListener{
            override fun onItemClick(bean: VideoInfoBean) {
                viewBinding.showClickHistory.visibility = VISIBLE
                viewBinding.showClickHistory.setData(bean)
            }
        })
    }

    fun reloadAdapter(){
        viewBinding.rvHistory.adapter = null
        val adapter = viewModel?.loadHistoryPage()?.let { FragmentHistoryAdapter(it) }
        viewBinding.rvHistory.adapter = adapter
    }

    fun scrollToTop() {
        viewBinding.rvHistory.smoothScrollToPosition(0)
    }

    fun releaseVideo(){
        for (item in historyList){
            if (item.currentState == StandardGSYVideoPlayer.CURRENT_STATE_PLAYING){
                item.onVideoPause()
                item.release()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        releaseVideo()
        viewBinding.rvHistory.adapter = null
        adapter = null
        viewModel?.onDestroy()
    }

}