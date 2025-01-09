package com.cccjka.demo.utils

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import com.cccjka.demo.application.MyApplication
import com.cccjka.demo.db.DbHelper
import com.google.gson.Gson

object CommonUtils {

    val gson: Gson = Gson()

    fun<T> navigation(context: Context, targetClass: Class<T>){
        val intent = Intent(context, targetClass)
        context.startActivity(intent)
    }

    fun toJson(convertString: Any): String{
        return gson.toJson(convertString)
    }

    fun<T> fromJson(convertString: String, cls: Class<T>): T{
        try {
            return gson.fromJson(convertString, cls)
        } catch (e: Exception){
            return gson.fromJson("", cls)
        }
    }

}