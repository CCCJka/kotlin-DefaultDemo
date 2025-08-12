package com.cccjka.demo.viewmodel

import androidx.lifecycle.ViewModel
import com.cccjka.demo.db.DbHelper
import com.cccjka.demo.db.bean.HistoryBean

class HistoryFragmentViewModel: ViewModel() {

    fun loadHistoryPage(): List<HistoryBean>{
        val historyList = DbHelper.get().getHistory().reversed()
        return historyList
    }

    fun onDestroy(){

    }
}