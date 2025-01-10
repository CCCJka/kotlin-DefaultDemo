package com.cccjka.demo.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.cccjka.demo.adapter.FragmentVideoAdapter
import com.cccjka.demo.databinding.FragmentHotBinding
import com.cccjka.demo.navigator.FragmentNavigator
import com.cccjka.demo.navigator.RequestPageNavigator
import com.cccjka.demo.viewmodel.HotFragmentViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HotFragment: Fragment(), RequestPageNavigator {

    private lateinit var viewBinding: FragmentHotBinding
    private lateinit var viewModel: HotFragmentViewModel
    private lateinit var adapter: FragmentVideoAdapter

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
        viewModel.requestHotPoint()
    }

    override fun loadPageInfo(list: List<String>) {
        CoroutineScope(Dispatchers.Main).launch {
            adapter = FragmentVideoAdapter(list)
            val linearLayout = LinearLayoutManager(context)
            viewBinding.rvHotPoint.layoutManager = linearLayout
            viewBinding.rvHotPoint.adapter = adapter
            viewBinding.rvHotPoint.setItemViewCacheSize(100);
        }
    }

    fun scrollToTop() {
        viewBinding.rvHotPoint.smoothScrollToPosition(0)
    }

    override fun onDestroy() {
        super.onDestroy()
        adapter.pauseVideo();
        viewModel.onDestory()
    }
}