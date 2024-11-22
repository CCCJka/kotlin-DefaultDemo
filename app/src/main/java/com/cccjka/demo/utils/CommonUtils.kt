package com.cccjka.demo.utils

import android.content.Context
import android.content.Intent
import com.cccjka.demo.view.CameraActivity

object CommonUtils {
    fun<T> navigation(context: Context, targetClass: Class<T>){
        val intent = Intent(context, targetClass)
        context.startActivity(intent)
    }
}