package com.cccjka.demo.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.cccjka.demo.application.MyApplication
import com.cccjka.demo.db.bean.HistoryBean
import com.cccjka.demo.utils.CommonUtils

class DbHelper private constructor(){

    companion object {
        private var instance: DbHelper? = null
            get() {
                if (field == null) {
                    field = DbHelper()
                }
                return field
            }
        @Synchronized
        fun get(): DbHelper{
            return instance!!
        }
    }

    val dbCommon = DbCommonClass(MyApplication.context, "HistoryTable.db", 1)

    fun init(){
        val db = dbCommon.writableDatabase
        val cursor = db.query("HistoryTable", null, null, null, null, null, null)
        if (cursor.count > 0){
            Log.i("", "数据库已存在")
        } else{
            Log.i("", "数据库创建成功")
        }
    }

    fun saveHistory(historyBean: HistoryBean){
        val db =  dbCommon.writableDatabase
        val historyList = getHistory()
        for (item in historyList){
            if (historyBean.name == item.name){
                db.delete("HistoryTable", "name=?", arrayOf(historyBean.name))
                //第二个参数String：where选择语句, 选择哪些行要被删除, 如果为null, 就删除所有行;
                //第三个参数String[]： where语句的参数, 逐个替换where语句中的 "?" 占位符;
            }
        }
        val history = ContentValues().apply {
            put("name", historyBean.name)
            put("url", historyBean.url)
            put("imgUrl", historyBean.imgUrl)
            put("description", historyBean.description)
            put("time", historyBean.time)
        }
        db.insert("HistoryTable", null, history)
    }

    fun getHistory(): List<HistoryBean>{
        var historyList: ArrayList<HistoryBean> = arrayListOf()
        val db = dbCommon.writableDatabase
        val cursor = db.query("HistoryTable", null, null, null, null, null, null)
        if (cursor.moveToFirst()){
            do {
                val name = cursor.getString(cursor.getColumnIndex("name"))
                val url = cursor.getString(cursor.getColumnIndex("url"))
                val imgUrl = cursor.getString(cursor.getColumnIndex("imgUrl"))
                val description = cursor.getString(cursor.getColumnIndex("description"))
                val time = cursor.getInt(cursor.getColumnIndex("time"))
                val history = HistoryBean(name, url, imgUrl, description, time)
                historyList.add(history)
            }while (cursor.moveToNext())
        }
        cursor.close()
        return historyList
    }

    fun release(){
        dbCommon.close()
    }

    class DbCommonClass(val context: Context, name: String, version: Int): SQLiteOpenHelper(context, name, null, version){
        private val createTable = "create table HistoryTable (" +
                " id integer primary key autoincrement," +
                " name text," +
                " url text," +
                " imgUrl text," +
                " description text," +
                " time integer)"

        override fun onCreate(p0: SQLiteDatabase?) {
            p0?.execSQL(createTable)
            Log.i("HistoryTableCteate", "Finish");
        }

        override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
            p0?.execSQL("drop table if exists HistoryTable")
            onCreate(p0)
        }
    }
}