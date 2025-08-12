package com.cccjka.demo.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cccjka.demo.databinding.ActivitySettingBinding

class SettingActivity: AppCompatActivity() {

    private lateinit var viewBinding: ActivitySettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        initAll()
    }

    private fun initAll(){

    }
}