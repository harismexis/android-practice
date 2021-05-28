package com.harismexis.breakingbad.kotlinflows

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class LatestNewsViewModel(
    private val newsRepository: NewsRepository
) : ViewModel() {

    init {
        viewModelScope.launch {
            newsRepository.favoriteLatestNews
                .catch { exception -> emit(lastCachedNews()) }
                .collect { news ->
                // Update View with latest news
            }
        }
    }

    fun lastCachedNews(): List<ArticleHeadline> {
        return listOf()
    }
}