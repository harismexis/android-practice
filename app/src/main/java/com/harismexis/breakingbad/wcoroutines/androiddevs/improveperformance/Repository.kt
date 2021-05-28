package com.harismexis.breakingbad.wcoroutines.androiddevs.improveperformance

import kotlinx.coroutines.*

class Repository(
    private val externalScope: CoroutineScope,
    private val ioDispatcher: CoroutineDispatcher
) {

    suspend fun doWorkLaunch() {
        withContext(ioDispatcher) {
            // doSomeOtherWork()
            externalScope.launch {
                // if this can throw an exception, wrap inside try/catch
                // or rely on a CoroutineExceptionHandler installed
                // in the externalScope's CoroutineScope
                veryImportantOperation()
            }.join()
        }
    }

    suspend fun doWorkAwait(): Any { // Use a specific type in Result
        return withContext(ioDispatcher) {
            // doSomeOtherWork()
            externalScope.async {
                // Exceptions are exposed when calling await, they will be
                // propagated in the coroutine that called doWork. Watch
                // out! They will be ignored if the calling context cancels.
                veryImportantOperation()
            }.await()
        }
    }

    private fun veryImportantOperation() {

    }

}