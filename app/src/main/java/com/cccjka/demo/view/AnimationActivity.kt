package com.cccjka.demo.view


import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cccjka.demo.R
import com.cccjka.demo.adapter.LeftSideMenuAdapter
import com.cccjka.demo.viewmodel.AnimationViewModel

class AnimationActivity: AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var rl_item: RecyclerView
    private lateinit var btn_log_out: Button

    private lateinit var viewmodel: AnimationViewModel

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
        viewmodel = AnimationViewModel()

        drawerLayout = findViewById(R.id.drawerlayout)
        btn_log_out = findViewById(R.id.btn_log_out)

        rl_item = findViewById(R.id.rl_item)
        val leftSideMenuAdapter = LeftSideMenuAdapter(list)
        val linearLayout = LinearLayoutManager(this)
        rl_item.layoutManager = linearLayout
        rl_item.adapter =  leftSideMenuAdapter

        btn_log_out.setOnClickListener{
            logOut()
        }

    }

    private fun logOut(){
        //TODO log out logic
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}
