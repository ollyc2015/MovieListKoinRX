package com.oliver_curtis.movies_list.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.verify
import com.oliver_curtis.movies_list.common.viewmodel.CallResult
import com.oliver_curtis.movies_list.domain.model.Movie
import com.oliver_curtis.movies_list.view.processor.MovieListProcessor
import com.oliver_curtis.movies_list.view.viewmodel.MovieViewModel
import io.reactivex.Single
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class MovieListFragmentTest {

    @Mock lateinit var movieListProcessor: MovieListProcessor
    @Mock lateinit var viewModel: MovieViewModel
    @Mock lateinit var movieObserver: Observer<CallResult<List<Movie>>>

    //Class under test
    private lateinit var movieListFragment: MovieListFragment

    private val movie = Movie(1, "/ugZW8ocsrfgI95pnQ7wrmKDxIe.jpg", "Hard Kill", 4.7, "2020-08-25")


    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        movieListFragment = MovieListFragment()
    }

    @Test
    fun `givenResults_WhenSuccessfulRequestMade_PassResultsToView`() {
        val expected = listOf(movie)

        // WHEN we get movies from the database, we expect to receive the value stated
        Mockito.`when`(viewModel.getMovies(1)).thenReturn(MutableLiveData(CallResult(expected)))

        // GIVEN movies actually requested from the DB
        viewModel.getMovies(1).observeForever(movieObserver)

        // run assertion against observer
        argumentCaptor<CallResult<List<Movie>>>().apply {
            verify(movieObserver).onChanged(capture())
        }.run { Assert.assertEquals(expected, firstValue.result()) }
    }

    @Test
    fun `givenResults_WhenFailureOccurred_PassFailureInfoToView`() {
        val expected = Throwable()

        // WHEN we get movies from the database, we expect to receive the value stated
        Mockito.`when`(viewModel.getMovies(1)).thenReturn(MutableLiveData(CallResult(expected)))

        // GIVEN movies actually requested from the DB
        viewModel.getMovies(1).observeForever(movieObserver)

        // run assertion against observer
        argumentCaptor<CallResult<List<Movie>>>().apply {
            verify(movieObserver).onChanged(capture())
        }.run { Assert.assertEquals(expected, firstValue.error()) }
    }

}