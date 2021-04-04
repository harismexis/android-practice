package com.harismexis.breakingbad.wcoroutines.androiddevs.bestpractices.interactors

import com.harismexis.breakingbad.wcoroutines.androiddevs.bestpractices.data.respository.AuthorsRepository
import com.harismexis.breakingbad.wcoroutines.androiddevs.bestpractices.data.respository.NewsRepository
import com.harismexis.breakingbad.wcoroutines.androiddevs.bestpractices.domain.ArticleWithAuthor
import com.harismexis.breakingbad.wcoroutines.androiddevs.bestpractices.result.ResultNewsWithAuthors

class GetLatestNewsWithAuthorsUseCase(
    private val newsRepository: NewsRepository,
    private val authorsRepository: AuthorsRepository
) {

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