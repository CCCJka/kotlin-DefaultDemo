package com.cccjka.demo.view


import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.cccjka.demo.R
import com.cccjka.demo.adapter.LeftSideMenuAdapter

class AnimationActivity: AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var rl_item: RelativeLayout
    private var leftSideMenuAdapter: LeftSideMenuAdapter ?= null

    val list = listOf("个人信息", "设置", "分享")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animation)
        initAll()
    }

    private fun initAll(){
        initData()
    }

    private fun initData(){
        drawerLayout = findViewById(R.id.drawerlayout)
        rl_item = findViewById(R.id.rl_item)
        rl_item
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}
