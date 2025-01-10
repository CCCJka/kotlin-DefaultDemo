package com.cccjka.demo.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.cccjka.demo.adapter.FragmentHistoryAdapter
import com.cccjka.demo.databinding.FragmentHistoryBinding
import com.cccjka.demo.navigator.FragmentNavigator
import com.cccjka.demo.navigator.RequestPageNavigator
import com.cccjka.demo.viewmodel.HistoryFragmentViewModel

class HistoryFragment: Fragment(), RequestPageNavigator {

    private lateinit var viewBinding: FragmentHistoryBinding
    private lateinit var viewModel: HistoryFragmentViewModel
    private lateinit var adapter: FragmentHistoryAdapter

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
        adapter = FragmentHistoryAdapter(viewModel.loadHistoryPage())
        val linearLayout = LinearLayoutManager(context)
        viewBinding.rvHistory.layoutManager = linearLayout
        viewBinding.rvHistory.adapter = adapter
        viewBinding.rvHistory.setItemViewCacheSize(100)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.onDestroy()
    }

    override fun loadPageInfo(list: List<String>) {

    }

    fun scrollToTop() {
        viewBinding.rvHistory.smoothScrollToPosition(0)
    }
}