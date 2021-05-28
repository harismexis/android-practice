package com.harismexis.breakingbad.wcoroutines.androiddevs.improveperformance

import kotlinx.coroutines.*

class ExampleClass4 {

    val scope = CoroutineScope(Job() + Dispatchers.Main)

    fun exampleMethod() {
        scope.launch {
            val def = scope.async {  }
            val result = def.await()

            supervisorScope {  }

            coroutineScope {  }
        }
    }

    suspend fun exampleMethod2() {
        scope.launch {
            val def = scope.async {  }
            val result = def.await()

            supervisorScope {
                launch {  }
                async {  }
            }

            coroutineScope {  }
        }
    }

}