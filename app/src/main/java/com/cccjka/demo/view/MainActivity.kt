package com.cccjka.demo.view

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.cccjka.demo.databinding.ActivityMainBinding
import com.cccjka.demo.utils.CommonUtils

class MainActivity : AppCompatActivity() {

    val PERMISSIONS_STORAGE: Array<String> = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    private lateinit var viewBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        initAll()
    }

    private fun initAll(){
        ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, PackageManager.PERMISSION_GRANTED)
        initView()
        initData()
    }

    private fun initView() {

    }

    private fun initData(){

        viewBinding.clickCamera.setOnClickListener{
            CommonUtils.navigation(this, CameraActivity::class.java)
        }
        viewBinding.clickWebview.setOnClickListener{
            CommonUtils.navigation(this, WebActivity::class.java)
        }
        viewBinding.clickMedia.setOnClickListener{
            CommonUtils.navigation(this, MediaActivity::class.java)
        }
        viewBinding.clickAnimation.setOnClickListener{
            CommonUtils.navigation(this,AnimationActivity::class.java)
        }
    }
}