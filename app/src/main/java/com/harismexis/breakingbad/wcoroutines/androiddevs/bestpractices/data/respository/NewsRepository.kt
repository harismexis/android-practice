package com.harismexis.breakingbad.wcoroutines.androiddevs.bestpractices.data.respository

import com.harismexis.breakingbad.wcoroutines.androiddevs.bestpractices.domain.Article
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

// DO inject Dispatchers
class NewsRepository(
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default,
    private val ioDispatcher: CoroutineDispatcher
) {

    suspend fun loadNews() = withContext(defaultDispatcher) {
    }

    // As this operation is manually retrieving the news from the server
    // using a blocking HttpURLConnection, it needs to move the execution
    // to an IO dispatcher to make it main-safe
    suspend fun fetchLatestNews(): List<Article> {
        withContext(ioDispatcher) {
            delay(3000)
        }
        return ArrayList()
    }
}