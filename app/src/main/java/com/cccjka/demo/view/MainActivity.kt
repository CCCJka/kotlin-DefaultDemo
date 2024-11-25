package com.cccjka.demo.view

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.cccjka.demo.R
import com.cccjka.demo.utils.CommonUtils

class MainActivity : AppCompatActivity() {

    val PERMISSIONS_STORAGE: Array<String> = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )


    private lateinit var btn_camera: Button
    private lateinit var btn_webView: Button
    private lateinit var btn_media: Button
    private lateinit var btn_animation: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        initAll()
    }

    private fun initAll(){
        ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, PackageManager.PERMISSION_GRANTED)
        initData()
    }

    private fun initData(){
        btn_camera = findViewById(R.id.click_camera)
        btn_webView = findViewById(R.id.click_webview)
        btn_media = findViewById(R.id.click_media)
        btn_animation = findViewById(R.id.click_animation)

        btn_camera.setOnClickListener{
            CommonUtils.navigation(this, CameraActivity::class.java)
        }
        btn_webView.setOnClickListener{
            CommonUtils.navigation(this, WebActivity::class.java)
        }
        btn_media.setOnClickListener{
            CommonUtils.navigation(this, MediaActivity::class.java)
        }
        btn_animation.setOnClickListener{
            CommonUtils.navigation(this,AnimationActivity::class.java)
        }
    }
}