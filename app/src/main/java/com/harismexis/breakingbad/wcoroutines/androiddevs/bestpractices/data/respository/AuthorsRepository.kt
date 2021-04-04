package com.harismexis.breakingbad.wcoroutines.androiddevs.bestpractices.data.respository

import com.harismexis.breakingbad.wcoroutines.androiddevs.bestpractices.domain.Author

class AuthorsRepository {

    fun getAuthor(author: String): Author {
        return Author(author, "Jack Napier")
    }

    fun getAllAuthors(): List<Author> {
        return ArrayList()
    }

}