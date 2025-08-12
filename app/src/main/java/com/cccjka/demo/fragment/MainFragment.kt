package com.cccjka.demo.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.cccjka.demo.adapter.FragmentVideoAdapter
import com.cccjka.demo.customUI.VideoPlayerView
import com.cccjka.demo.databinding.FragmentMainBinding
import com.cccjka.demo.listener.VideoClickListener
import com.cccjka.demo.navigator.RequestPageNavigator
import com.cccjka.demo.response.videobean.VideoInfoBean
import com.cccjka.demo.viewmodel.MainFragmentViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainFragment: Fragment(), RequestPageNavigator {


    private lateinit var viewBinding: FragmentMainBinding

    private var adapter: FragmentVideoAdapter? = null

    private var viewmodel: MainFragmentViewModel? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewBinding = FragmentMainBinding.inflate(layoutInflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init(){
        viewmodel = MainFragmentViewModel(this)
        viewmodel?.requestPageInfo()
    }

    override fun loadPageInfo(list: List<String>) {
        CoroutineScope(Dispatchers.Main).launch {
            adapter = FragmentVideoAdapter(list)
            val linearLayout = LinearLayoutManager(context)
            viewBinding.rvMainContent.layoutManager = linearLayout
            viewBinding.rvMainContent.adapter = adapter
            viewBinding.rvMainContent.setItemViewCacheSize(100)
            adapter?.setOnClickListener(object : VideoClickListener{
                override fun onItemClick(bean: VideoInfoBean) {
                    viewBinding.showClickVideo.visibility = View.VISIBLE
                    viewBinding.showClickVideo.setData(bean)
                }
            })
        }
    }

    fun scrollToTop() {
        viewBinding.rvMainContent.smoothScrollToPosition(0)
    }

    fun currentState() = viewBinding.showClickVideo.currentState()

    fun pauseVideo() {
        viewBinding.showClickVideo.pauseVideo()
    }

    fun resumeVideo() {
        viewBinding.showClickVideo.resumeVideo()
    }

    fun releaseVideo(){
        if (this::viewBinding.isInitialized){
            viewBinding.showClickVideo.setVisiblyGone()
            viewBinding.showClickVideo.pauseVideo()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        releaseVideo()
        viewBinding.rvMainContent.adapter = null
        adapter?.onDestroy()
        adapter = null
        viewmodel?.onDestroy()
    }
}