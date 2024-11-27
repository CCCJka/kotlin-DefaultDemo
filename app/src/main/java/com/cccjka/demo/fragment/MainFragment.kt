package com.cccjka.demo.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cccjka.demo.R
import com.cccjka.demo.adapter.MainFragmentAdapter

class MainFragment: Fragment() {

    //模拟网络请求获得的数据
    val list = listOf("{\"title\":\"aiosjgoiajsg\", \"content\":\"asigjosijdg\", \"img\":1}",
        "{\"title\":\"aiosjgoiajsg\", \"content\":\"asigjosijdg\", \"img\":1}",
        "{\"title\":\"aiosjgoiajsg\", \"content\":\"asigjosijdg\", \"img\":1}",
        "{\"title\":\"aiosjgoiajsg\", \"content\":\"asigjosijdg\", \"img\":1}",
        "{\"title\":\"aiosjgoiajsg\", \"content\":\"asigjosijdg\", \"img\":1}",
        "{\"title\":\"aiosjgoiajsg\", \"content\":\"asigjosijdg\", \"img\":1}",
        "{\"title\":\"aiosjgoiajsg\", \"content\":\"asigjosijdg\", \"img\":1}",
        "{\"title\":\"aiosjgoiajsg\", \"content\":\"asigjosijdg\", \"img\":1}")

    private lateinit var rv_content: RecyclerView

    private lateinit var adapter: MainFragmentAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val click_to_top = view.findViewById<Button>(R.id.btn_to_top)
        click_to_top.setOnClickListener{
            rv_content.smoothScrollToPosition(0)
        }
        rv_content = view.findViewById(R.id.rv_content)
        init(view.context)
    }

    private fun init(context: Context){
        adapter = MainFragmentAdapter(list)
        val linearLayout = LinearLayoutManager(context)
        rv_content.layoutManager = linearLayout
        rv_content.adapter = adapter
    }
}