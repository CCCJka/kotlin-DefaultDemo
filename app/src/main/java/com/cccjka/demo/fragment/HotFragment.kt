package com.cccjka.demo.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.cccjka.demo.adapter.FragmentVideoAdapter
import com.cccjka.demo.databinding.FragmentHotBinding
import com.cccjka.demo.listener.VideoClickListener
import com.cccjka.demo.navigator.FragmentNavigator
import com.cccjka.demo.navigator.RequestPageNavigator
import com.cccjka.demo.response.videobean.VideoInfoBean
import com.cccjka.demo.viewmodel.HotFragmentViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HotFragment: Fragment(), RequestPageNavigator {

    private lateinit var viewBinding: FragmentHotBinding
    private var viewModel: HotFragmentViewModel? = null
    private var adapter: FragmentVideoAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewBinding = FragmentHotBinding.inflate(layoutInflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init();
    }

    fun init(){
        viewModel = HotFragmentViewModel(this)
        viewModel?.requestHotPoint()
    }

    override fun loadPageInfo(list: List<String>) {
        CoroutineScope(Dispatchers.Main).launch {
            val linearLayout = LinearLayoutManager(context)
            adapter = FragmentVideoAdapter(list)
            viewBinding.rvHotPoint.layoutManager = linearLayout
            viewBinding.rvHotPoint.adapter = adapter
            viewBinding.rvHotPoint.setItemViewCacheSize(100)
            adapter?.setOnClickListener(object : VideoClickListener{
                override fun onItemClick(bean: VideoInfoBean) {
                    viewBinding.showClickHot.visibility = View.VISIBLE
                    viewBinding.showClickHot.setData(bean)
                }
            })
        }
    }

    fun currentState() = viewBinding.showClickHot.currentState()

    fun pauseVideo(){
        viewBinding.showClickHot.pauseVideo()
    }

    fun resumeVideo(){
        viewBinding.showClickHot.resumeVideo()
    }

    fun scrollToTop() {
        viewBinding.rvHotPoint.smoothScrollToPosition(0)
    }

    fun releaseVideo(){
        if (this::viewBinding.isInitialized){
            viewBinding.showClickHot.setVisiblyGone()
            viewBinding.showClickHot.pauseVideo()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        releaseVideo()
        viewBinding.rvHotPoint.adapter = null
        adapter?.onDestroy()
        adapter = null
        viewModel?.onDestory()
    }
}