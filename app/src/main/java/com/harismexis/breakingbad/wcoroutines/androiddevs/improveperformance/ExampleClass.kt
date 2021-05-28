package com.harismexis.breakingbad.wcoroutines.androiddevs.improveperformance

import android.util.Log
import com.harismexis.breakingbad.framework.extensions.getErrorMessage
import kotlinx.coroutines.*

class ExampleClass {

    // Job and Dispatcher are combined into a CoroutineContext which
    // will be discussed shortly
    val scope = CoroutineScope(
        Job()
                + Dispatchers.Default
                + CoroutineName("Coroutine1")
                + CoroutineExceptionHandler { _, throwable -> Log.d("", throwable.getErrorMessage())
        })

    suspend fun exampleMethod() {
        // Starts a new coroutine within the scope
        scope.launch {
            // New coroutine that can call suspend functions
            // fetchDocs()
        }

        val job2 = scope.launch(Dispatchers.Default + CoroutineName("")) {
            // New coroutine with CoroutineName = "BackgroundCoroutine" (overridden)
        }

        // Obsolete
        // val myContext = newFixedThreadPoolContext(nThreads = 3, name = "My JDBC context")
        withContext(Job() + Dispatchers.IO + CoroutineName("name")) {
            //
        }
    }

    fun cleanUp() {
        // Cancel the scope to cancel ongoing coroutines work
        scope.cancel()
    }

}