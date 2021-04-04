package com.harismexis.breakingbad.wcoroutines.androiddevs.bestpractices.interactors

import com.harismexis.breakingbad.wcoroutines.androiddevs.bestpractices.data.respository.AuthorsRepository
import com.harismexis.breakingbad.wcoroutines.androiddevs.bestpractices.data.respository.BooksRepository
import com.harismexis.breakingbad.wcoroutines.androiddevs.bestpractices.domain.BooksAndAuthors
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class GetAllBooksAndAuthorsUseCase(
    private val booksRepository: BooksRepository,
    private val authorsRepository: AuthorsRepository,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default
) {

    /*
    If the work to be done in those coroutines are relevant only when the user is
    present on the current screen, it should follow the caller's lifecycle.
    In most cases, the caller will be the ViewModel. In this case, coroutineScope or
    supervisorScope should be used.
     */
    suspend fun getBookAndAuthors(): BooksAndAuthors {

        // In parallel, fetch books and authors and return when both requests
        // complete and the data is ready
        return coroutineScope {

            val books = async(defaultDispatcher) {
                booksRepository.getAllBooks()
            }

            val authors = async(defaultDispatcher) {
                authorsRepository.getAllAuthors()
            }

            BooksAndAuthors(books.await(), authors.await())
        }
    }
}