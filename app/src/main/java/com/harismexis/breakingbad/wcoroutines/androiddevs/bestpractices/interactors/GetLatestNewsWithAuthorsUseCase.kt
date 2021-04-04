package com.harismexis.breakingbad.wcoroutines.androiddevs.bestpractices.interactors

import com.harismexis.breakingbad.wcoroutines.androiddevs.bestpractices.data.respository.AuthorsRepository
import com.harismexis.breakingbad.wcoroutines.androiddevs.bestpractices.data.respository.NewsRepository
import com.harismexis.breakingbad.wcoroutines.androiddevs.bestpractices.domain.ArticleWithAuthor
import com.harismexis.breakingbad.wcoroutines.androiddevs.bestpractices.result.ResultNewsWithAuthors

class GetLatestNewsWithAuthorsUseCase(
    private val newsRepository: NewsRepository,
    private val authorsRepository: AuthorsRepository
) {

    // This method doesn't need to worry about moving the execution of the
    // coroutine to a different thread as newsRepository is main-safe.
    // The work done in the coroutine is lightweight as it only creates
    // a list and add elements to it
    suspend operator fun invoke(): ResultNewsWithAuthors {
        // this call is main safe
        val news = newsRepository.fetchLatestNews()

        val response: MutableList<ArticleWithAuthor> = mutableListOf()
        for (article in news) {
            val author = authorsRepository.getAuthor(article.author)
            response.add(ArticleWithAuthor(article, author))
        }
        return ResultNewsWithAuthors.Success(response)
    }

}