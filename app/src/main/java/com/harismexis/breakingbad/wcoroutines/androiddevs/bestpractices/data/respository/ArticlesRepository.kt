package com.harismexis.breakingbad.wcoroutines.androiddevs.bestpractices.data.respository

import com.harismexis.breakingbad.wcoroutines.androiddevs.bestpractices.data.datasource.ArticlesDataSource
import com.harismexis.breakingbad.wcoroutines.androiddevs.bestpractices.domain.Article
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ArticlesRepository(
    private val articlesDataSource: ArticlesDataSource,
    private val externalScope: CoroutineScope, // should be created and managed by a class that lives
    // longer than the current screen, it could be managed by the Application class or a ViewModel
    // scoped to a navigation graph
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default
) {
    // As we want to complete bookmarking the article even if the user moves
    // away from the screen, the work is done creating a new coroutine
    // from an external scope
    suspend fun bookmarkArticle(article: Article) {
        externalScope.launch(defaultDispatcher) {
            articlesDataSource.bookmarkArticle(article)
        }.join() // Wait for the coroutine to complete
    }
}