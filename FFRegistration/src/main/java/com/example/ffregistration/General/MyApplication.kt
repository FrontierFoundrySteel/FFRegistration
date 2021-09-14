package com.example.ffregistration.General

import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast

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
        Toast.makeText(applicationContext,"Application class ran",Toast.LENGTH_SHORT).show()
        Log.d("MyApplication ", " start")
    }
}
