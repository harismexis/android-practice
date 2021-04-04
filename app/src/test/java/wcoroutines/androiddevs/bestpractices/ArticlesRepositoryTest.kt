package wcoroutines.androiddevs.bestpractices

import com.google.common.truth.Truth.assertThat
import com.harismexis.breakingbad.wcoroutines.androiddevs.bestpractices.data.datasource.ArticlesDataSource
import com.harismexis.breakingbad.wcoroutines.androiddevs.bestpractices.data.respository.ArticlesRepository
import com.harismexis.breakingbad.wcoroutines.androiddevs.bestpractices.domain.Article
import io.mockk.mockk
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

class ArticlesRepositoryTest {

    @ExperimentalCoroutinesApi
    private val testDispatcher = TestCoroutineDispatcher()

    @ExperimentalCoroutinesApi
    @Test
    fun testBookmarkArticle() {
        // Execute all coroutines that use this Dispatcher immediately
        testDispatcher.runBlockingTest {
            // given
            // val articlesDataSource = FakeArticlesDataSource()
            val articlesDataSource: ArticlesDataSource = mockk(relaxed=true)

            val repository = ArticlesRepository(
                articlesDataSource,
                CoroutineScope(testDispatcher), // use the same dispatcher with runBlockingTest
                testDispatcher
            )
            val article = Article("", "", "", "", "")

            // when
            repository.bookmarkArticle(article)

            // then
            assertThat(articlesDataSource.isBookmarked(article)).isTrue()
        }
        // make sure nothing else is scheduled to be executed
        testDispatcher.cleanupTestCoroutines()
    }
}