package com.harismexis.breakingbad.wcoroutines.androiddevs.improveperformance

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ExampleClass3 {

    val scope = CoroutineScope(Job() + Dispatchers.Main)

    fun exampleMethod() {
        // Starts a new coroutine on Dispatchers.Main as it's the scope's default
        val job1 = scope.launch {
            // New coroutine with CoroutineName = "coroutine" (default)
        }

        // Starts a new coroutine on Dispatchers.Default
//        val job2 = scope.launch(Dispatchers.Default + "BackgroundCoroutine") {
//            // New coroutine with CoroutineName = "BackgroundCoroutine" (overridden)
//        }
    }

}