package com.ffsteel.ffpractice_app1.Services

import android.os.Build
import android.util.Log
import com.example.ffregistration.General.MyApplication
import com.example.ffregistration.General.PrefManager
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit

object ServiceBuilder {
    // Before release, change this URL to your live server URL such as "http://ff.com.pk/FCM/UserRegistration.php/"

     //var URL = PrefManager(MyApplication.applicationContext()).GetFireStoreLink()

    fun URL(): String {
        val result =when(PrefManager(MyApplication.applicationContext()).GetFireStoreLink().length>5) {
            true  -> PrefManager(MyApplication.applicationContext()).GetFireStoreLink()
            false -> PrefManager(MyApplication.applicationContext()).GetFireStoreLinkbu()
        }

        if(PrefManager(MyApplication.applicationContext()).GetFireStoreP().length>5) {
        }
        else{
            PrefManager(MyApplication.applicationContext()).SetFireStoreP(PrefManager(MyApplication.applicationContext()).GetFireStorePbu())
        }

        Log.d("Linkoo","Linkoo# $result ${PrefManager(MyApplication.applicationContext()).GetFireStoreLink().length>5}")
        //Toast.makeText(MyApplication.applicationContext(),"Linkoo $result",Toast.LENGTH_LONG).show()
        return result
    }

    // Create Logger
    private val logger = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    // Create a Custom Interceptor to apply Headers application wide
    val headerInterceptor = object: Interceptor {

        override fun intercept(chain: Interceptor.Chain): Response {

            var request = chain.request()

            request = request.newBuilder()
                    .addHeader("x-device-type", Build.DEVICE)
                    .addHeader("Accept-Language", Locale.getDefault().language)
                    .build()

            val response = chain.proceed(request)
            return response
        }
    }

    // Create OkHttp Client
    private val okHttp = OkHttpClient.Builder()
            .callTimeout(5, TimeUnit.SECONDS)
            .addInterceptor(headerInterceptor)
            .addInterceptor(logger)

    // Create Retrofit Builder
    private val builder = Retrofit.Builder()
            .baseUrl(URL())
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttp.build())

    // Create Retrofit Instance
    private val retrofit = builder.build()

    fun <T> buildService(serviceType: Class<T>): T {
        return retrofit.create(serviceType)
    }
}