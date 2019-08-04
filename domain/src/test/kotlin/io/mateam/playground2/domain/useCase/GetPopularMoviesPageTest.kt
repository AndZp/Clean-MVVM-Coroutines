package io.mateam.playground2.domain.useCase

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.whenever
import io.mateam.playground2.domain.entity.movie.PopularMovies
import io.mateam.playground2.domain.entity.result.Failure
import io.mateam.playground2.domain.entity.result.Result
import io.mateam.playground2.domain.repo.MoviesRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import java.io.IOException

@ExperimentalCoroutinesApi
class GetPopularMoviesPageTest {

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    private lateinit var getMoviesUseCase: GetPopularMoviesPage
    private var moviesRepoMock = Mockito.mock(MoviesRepo::class.java)

    private lateinit var params: GetPopularMoviesPage.Param
    private lateinit var repoResponse: Result<PopularMovies>

    private lateinit var expectedUseCaseSuccessResult: Result<PopularMovies>
    private lateinit var expectedUseCaseFailureResult: Failure


    private val popularMoviesPage = PopularMovies(page = 1, movies = emptyList(), totalMovies = 0, totalPages = 1)
    private val repoException = IOException("Test IO exception")


    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
        getMoviesUseCase = GetPopularMoviesPage(moviesRepoMock)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @Test
    fun `check getPopularMovies - load first page - return Success with first page loaded`() {
        `given repository that able to return one page`()

        `when getPopularMoviesPage with parameter page = 1 invoked`()

        `than verify that repo getPopularMovies() called once with  parameter page = 1`()
        `that verify useCase invoke result is Success with expected data`()
    }

    @Test
    fun `check getPopularMovies - repo exception - return GetPopularFailure LoadError`() {
        `given Repository return Result Error with Exception as arg`()

        `when getPopularMoviesPage with parameter page = 1 invoked`()

        `than verify that repo getPopularMovies() called once with  parameter page = 1`()
        `that verify useCase invoke result is GetPopularFailure LoadError - with repoException`()
    }

    @Test
    fun `check getPopularMovies - end reached  - return GetPopularFailure EndReached`() {
        `given repository that able to return one page`()

        `when getPopularMoviesPage with parameter page = 1 invoked`()
        `when getPopularMoviesPage with parameter page = 2 invoked`()

        `than verify that repo getPopularMovies() called once with  parameter page = 1`()
        `that verify useCase invoke result is GetPopularFailure EndReached`()
    }

    private fun `given Repository return Result Error with Exception as arg`() {
        runBlocking {
            repoResponse = Result.Error(repoException)
            whenever(moviesRepoMock.getPopularMovies(any())).thenReturn(repoResponse)
        }
    }

    private fun `given repository that able to return one page`() = runBlocking {
        repoResponse = Result.Success(popularMoviesPage)
        whenever(moviesRepoMock.getPopularMovies(any())).thenReturn(repoResponse)
    }

    private fun `when getPopularMoviesPage with parameter page = 1 invoked`() = runBlocking {
        params = GetPopularMoviesPage.Param(1)
        getMoviesUseCase(this, params) { either ->
            either.either(
                fun(failure: Failure) {
                    expectedUseCaseFailureResult = failure
                },
                fun(movies: PopularMovies) {
                    expectedUseCaseSuccessResult = Result.Success(movies)
                })
        }
    }

    private fun `when getPopularMoviesPage with parameter page = 2 invoked`() = runBlocking {
        params = GetPopularMoviesPage.Param(2)
        getMoviesUseCase(this, params) { either ->
            either.either(
                fun(failure: Failure) {
                    expectedUseCaseFailureResult = failure
                },
                fun(movies: PopularMovies) {
                    expectedUseCaseSuccessResult = Result.Success(movies)
                })
        }
    }

    private fun `than verify that repo getPopularMovies() called once with  parameter page = 1`() {
        runBlocking { verify(moviesRepoMock).getPopularMovies(1) }
        verifyNoMoreInteractions(moviesRepoMock)
    }

    private fun `that verify useCase invoke result is Success with expected data`() {
        assertThat(expectedUseCaseSuccessResult).isEqualTo(Result.Success(popularMoviesPage))
    }

    private fun `that verify useCase invoke result is GetPopularFailure LoadError - with repoException`() {
        assertThat(expectedUseCaseFailureResult).isEqualTo(
            GetPopularMoviesPage.GetPopularFailure.LoadError(
                repoException
            )
        )
    }

    private fun `that verify useCase invoke result is GetPopularFailure EndReached`() {
        assertThat(expectedUseCaseFailureResult).isEqualTo(GetPopularMoviesPage.GetPopularFailure.EndReached)
    }
}


