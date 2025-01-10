package com.cccjka.demo.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.cccjka.demo.adapter.FragmentVideoAdapter
import com.cccjka.demo.databinding.FragmentMainBinding
import com.cccjka.demo.navigator.RequestPageNavigator
import com.cccjka.demo.viewmodel.MainFragmentViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainFragment: Fragment(), RequestPageNavigator {


    private lateinit var viewBinding: FragmentMainBinding

    private lateinit var adapter: FragmentVideoAdapter

    private lateinit var viewmodel: MainFragmentViewModel

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
        viewmodel.requestPageInfo()
    }

    override fun loadPageInfo(list: List<String>) {
        CoroutineScope(Dispatchers.Main).launch {
            adapter = FragmentVideoAdapter(list)
            val linearLayout = LinearLayoutManager(context)
            viewBinding.rvMainContent.layoutManager = linearLayout
            viewBinding.rvMainContent.adapter = adapter
            viewBinding.rvMainContent.setItemViewCacheSize(100);
        }
    }

    fun scrollToTop() {
        viewBinding.rvMainContent.smoothScrollToPosition(0)
    }

    override fun onDestroy() {
        super.onDestroy()
        adapter.pauseVideo()
        adapter.onDestroy()
        viewmodel.onDestroy()
    }
}