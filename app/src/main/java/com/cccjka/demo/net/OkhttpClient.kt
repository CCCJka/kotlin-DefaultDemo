package com.cccjka.demo.net

import android.os.Build
import com.cccjka.demo.application.MyApplication
import com.cccjka.demo.common.BaseInfo
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import java.io.File
import java.util.concurrent.TimeUnit

object OkhttpClient {

    val service: api by lazy (LazyThreadSafetyMode.SYNCHRONIZED){
        getRetrofit().create(api::class.java)
    }

    private var token:String by Preference("token","")

    private fun getRetrofit(): Retrofit {
        // 获取retrofit的实例
        return Retrofit.Builder()
            .baseUrl(BaseInfo.BASE_URL)  //自己配置
            .client(getOkHttpClient())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }

    private fun getOkHttpClient(): OkHttpClient {
        //添加一个log拦截器,打印所有的log
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        //可以设置请求过滤的水平,body,basic,headers
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        //设置 请求的缓存的大小跟位置
        val cacheFile = File(MyApplication.context.cacheDir, "cache")
        val cache = Cache(cacheFile, 1024 * 1024 * 50) //50Mb 缓存的大小

        return OkHttpClient.Builder()
            .addInterceptor(addQueryParameterInterceptor())  //参数添加
            .addInterceptor(addHeaderInterceptor()) // token过滤
//              .addInterceptor(addCacheInterceptor())
            .addInterceptor(httpLoggingInterceptor) //日志,所有的请求响应度看到
            .cache(cache)  //添加缓存
            .connectTimeout(60L, TimeUnit.SECONDS)
            .readTimeout(60L, TimeUnit.SECONDS)
            .writeTimeout(60L, TimeUnit.SECONDS)
            .build()
    }

    /**
     * 设置公共参数
     */
    private fun addQueryParameterInterceptor(): Interceptor {
        return Interceptor { chain ->
            val originalRequest = chain.request()
            val request: Request
            val modifiedUrl = originalRequest.url.newBuilder()
                // Provide your custom parameter here
                .addQueryParameter("udid", "")
                .addQueryParameter("deviceModel", getMobileModel())
                .build()
            request = originalRequest.newBuilder().url(modifiedUrl).build()
            chain.proceed(request)
        }
    }

    /**
     * 设置头
     */
    private fun addHeaderInterceptor(): Interceptor {
        return Interceptor { chain ->
            val originalRequest = chain.request()
            val requestBuilder = originalRequest.newBuilder()
                // Provide your custom header here
                .header("token", token)
                .method(originalRequest.method, originalRequest.body)
            val request = requestBuilder.build()
            chain.proceed(request)
        }
    }

    fun getMobileModel(): String {
        var model: String? = Build.MODEL
        model = model?.trim { it <= ' ' } ?: ""
        return model
    }

}