package com.example.ffregistration.General

import android.app.Application
import android.content.Context
import android.util.Log

class MyApplication : Application() {

    init {
        instance = this
    }
    companion object {
        private var instance: MyApplication? = null

        fun applicationContext() : Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        Log.d("MyApplication ", " start")
    }
}
