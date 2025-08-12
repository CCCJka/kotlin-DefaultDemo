package com.cccjka.demo.view

import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toFile
import androidx.core.net.toUri
import com.cccjka.demo.R
import com.cccjka.demo.databinding.ActivityMediaBinding
import java.io.File

class MediaActivity: AppCompatActivity() {

    val REQUEST_CODE_GET_CONTENT = 1001

    private lateinit var viewBinding: ActivityMediaBinding

    var uri: Uri? = null
    var mediaPlayer = MediaPlayer().apply {
        setAudioAttributes(     //设置音频
            AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .build()
        )
    }

    var surfaceCallBack = object : SurfaceHolder.Callback {
        override fun surfaceCreated(p0: SurfaceHolder) {
            uri?.let {
                mediaPlayer.setDataSource(this@MediaActivity, it)//设置视频地址
                mediaPlayer.prepareAsync()
                mediaPlayer.setSurface(p0.surface)
                mediaPlayer.setDisplay(p0)
                mediaPlayer.setOnPreparedListener{  //当prepareAsync执行完后将视频播放
                    it.isLooping = true //循环播放
                    it.start()      //视频开始播放但是画面是铺满SurfaceView的，任意造成视频拉伸观感不佳
                }
            }
        }
        override fun surfaceChanged(p0: SurfaceHolder, p1: Int, p2: Int, p3: Int) {}
        override fun surfaceDestroyed(p0: SurfaceHolder) {}
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMediaBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        initAll()
    }

    private fun initAll(){
        initView()
        initData()
    }

    private fun initView() {

    }

    private fun initData(){
        viewBinding.btnSelect.setOnClickListener{
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "*/*" //不限制选择的文件类型
//            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)    //多选
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            startActivityForResult(intent, REQUEST_CODE_GET_CONTENT)
        }

        viewBinding.btnShowSelect.setOnClickListener{
            viewBinding.btnSelect.visibility = View.VISIBLE
            viewBinding.btnShowSelect.visibility = View.GONE
            viewBinding.svMedia.visibility = View.GONE
            mediaPlayer.stop()
        }

//        //使用VideoView进行视频播放,画面不拉伸播放
//        val videoView = VideoView(this)
//        videoView.setVideoPath(file.path)
//        videoView.start()
//        videoView.setOnPreparedListener{
//        it.isLooping = true
//        }
//        rl_media.addView(videoView)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_GET_CONTENT && resultCode == RESULT_OK){
            data?.let {
                uri = data.data
                viewBinding.svMedia.holder?.let{
                    viewBinding.svMedia.holder.removeCallback(surfaceCallBack)
                }
                viewBinding.svMedia.holder.addCallback(surfaceCallBack)
                viewBinding.btnSelect.visibility = View.GONE
                viewBinding.svMedia.visibility = View.VISIBLE
                viewBinding.btnShowSelect.visibility = View.VISIBLE
            }

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.let {
            mediaPlayer.stop()
            mediaPlayer.release()
        }
        uri = null
    }

}
