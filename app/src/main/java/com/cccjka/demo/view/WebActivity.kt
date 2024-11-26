package com.cccjka.demo.view

import android.annotation.SuppressLint
import android.net.http.SslError
import android.os.Bundle
import android.view.View
import android.webkit.ClientCertRequest
import android.webkit.SslErrorHandler
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.EditText
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isEmpty
import com.cccjka.demo.R

class WebActivity: AppCompatActivity() {

    private lateinit var webContainer: RelativeLayout

    private lateinit var et_search: EditText
    private lateinit var btn_search: Button
    private lateinit var btn_show_search: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)
        initAll()
    }

    private fun initAll(){
        initView()
        initData()
        initWeb("")
    }

    private fun initView() {
        webContainer = findViewById(R.id.web_container)
        et_search = findViewById(R.id.et_search_content)
        btn_search = findViewById(R.id.btn_search)
        btn_show_search = findViewById(R.id.btn_show_search)
    }

    private fun initData(){
        btn_search.setOnClickListener{
            btn_search.visibility = View.GONE
            et_search.visibility = View.GONE
            btn_show_search.visibility = View.VISIBLE
            webContainer.visibility = View.VISIBLE
            initWeb(et_search.text.toString())
            et_search.setText("")
        }
        btn_show_search.setOnClickListener{
            btn_search.visibility = View.VISIBLE
            et_search.visibility = View.VISIBLE
            btn_show_search.visibility = View.GONE
            webContainer.visibility = View.GONE
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initWeb(url: String){
        val webView = WebView(this)
        webView.settings.cacheMode = WebSettings.LOAD_NO_CACHE
        webView.settings.domStorageEnabled = true
        webView.settings.javaScriptEnabled = true
        val webViewClient = object: WebViewClient(){    //打开https连接需要信任证书，创建一个webView客户端后就可以加载
            override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
                super.onReceivedSslError(view, handler, error)
                handler?.let{
                    handler.proceed()
                }
            }
        }
        webView.webViewClient = webViewClient
        if (url == null || url.equals("")){
            webView.loadUrl("web_site_addr")
            webContainer.addView(webView)
        } else{
            webContainer.removeAllViews()
            webView.loadUrl(url)
            webContainer.addView(webView)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        webContainer.removeAllViews()

    }
}