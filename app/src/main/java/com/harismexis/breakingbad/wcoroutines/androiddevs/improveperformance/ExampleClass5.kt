package com.harismexis.breakingbad.wcoroutines.androiddevs.improveperformance

import android.util.Log
import kotlinx.coroutines.*

class ExampleClass5 {

    val scope = CoroutineScope(
        Job()
                + Dispatchers.Default
                + CoroutineName("Coroutine1")
                + CoroutineExceptionHandler { _, _ -> Log.d("", "error") }
    )

    suspend fun exampleMethod() {
        scope.launch {  }
        coroutineScope {  }
    }

    fun clear() {
        scope.cancel()
    }

}