package com.cccjka.demo.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cccjka.demo.databinding.ActivityPersonInfoBinding

class PersonInfoActivity: AppCompatActivity() {

    private lateinit var viewBinding: ActivityPersonInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityPersonInfoBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        initAll()
    }

    private fun initAll(){

    }

}