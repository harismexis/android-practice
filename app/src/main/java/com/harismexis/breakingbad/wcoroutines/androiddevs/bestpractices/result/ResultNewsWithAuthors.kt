package com.harismexis.breakingbad.wcoroutines.androiddevs.bestpractices.result

import com.harismexis.breakingbad.wcoroutines.androiddevs.bestpractices.domain.ArticleWithAuthor

sealed class ResultNewsWithAuthors {
    data class Success(val items: List<ArticleWithAuthor>): ResultNewsWithAuthors()
    data class Error(val error: Exception): ResultNewsWithAuthors()
}