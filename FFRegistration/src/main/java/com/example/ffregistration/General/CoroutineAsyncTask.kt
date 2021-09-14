package com.example.ffregistration.General

import kotlinx.coroutines.*

abstract class CoroutineAsyncTask <Params, Progress, Result>{

    open fun onPreExecute(){}

    abstract fun doInBackground(vararg params: Params?): Result

    open fun  onProgressUpdate(vararg values: Progress?) {}

    open fun onPostExecute(result: Result?){}

    open fun onCancelled(result: Result?){}

    var isCanceled=false

    protected fun publishPorgress(vararg progress: Progress?){
        CoroutineScope(Dispatchers.Main).launch{//(Dispatchers.Main)
            onProgressUpdate(*progress)
        }
    }

    fun execute(vararg params: Params?){
        CoroutineScope(Dispatchers.IO).launch {//(Dispatchers.Default)

            withContext(Dispatchers.Main){
                onPreExecute()
            }

            val result =doInBackground(*params)

            withContext(Dispatchers.Main){
                onPostExecute(result)
            }
        }
    }

    fun cancel(mayInterruptIfRunning: Boolean){
    }
}