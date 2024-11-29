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
import com.cccjka.demo.databinding.ActivityWebBinding

class WebActivity: AppCompatActivity() {

    private lateinit var viewBinding: ActivityWebBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityWebBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        initAll()
    }

    private fun initAll(){
        initView()
        initData()
        initWeb("")
    }

    private fun initView() {

    }

    private fun initData(){
        viewBinding.btnSearch.setOnClickListener{
            viewBinding.btnSearch.visibility = View.GONE
            viewBinding.etSearchContent.visibility = View.GONE
            viewBinding.btnShowSearch.visibility = View.VISIBLE
            viewBinding.webContainer.visibility = View.VISIBLE
            initWeb(viewBinding.etSearchContent.text.toString())
            viewBinding.etSearchContent.setText("")
        }
        viewBinding.btnShowSearch.setOnClickListener{
            viewBinding.btnSearch.visibility = View.VISIBLE
            viewBinding.etSearchContent.visibility = View.VISIBLE
            viewBinding.btnShowSearch.visibility = View.GONE
            viewBinding.webContainer.visibility = View.GONE
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
            viewBinding.webContainer.addView(webView)
        } else{
            viewBinding.webContainer.removeAllViews()
            webView.loadUrl(url)
            viewBinding.webContainer.addView(webView)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewBinding.webContainer.removeAllViews()
    }
}