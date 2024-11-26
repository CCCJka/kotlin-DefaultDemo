package com.cccjka.demo.dialog

import android.app.Dialog
import android.content.Context
import android.widget.Button
import com.cccjka.demo.R

class ShareDialog: Dialog {

    private lateinit var confirm: Button

    constructor(context: Context) : super(context) {
        initView()
    }

    private fun initView(){
        setContentView(R.layout.dialog_share)
        setCanceledOnTouchOutside(false)
        confirm = findViewById(R.id.btn_dialog_confirm)
        confirm.setOnClickListener{
            cancel()
            dismiss()
        }
    }


}