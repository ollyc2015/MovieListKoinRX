package com.oliver_curtis.movies_list.data.repo

import com.oliver_curtis.movies_list.data.entity.server.MovieApiEntity
import com.oliver_curtis.movies_list.data.entity.server.MovieDetailsApiEntity
import com.oliver_curtis.movies_list.data.source.MovieCache
import com.oliver_curtis.movies_list.data.source.MovieDataSource
import com.oliver_curtis.movies_list.domain.model.Movie
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations


class MovieDBRepositoryTest {

    @Mock
    lateinit var localDB: MovieCache
    @Mock
    lateinit var remoteDB: MovieDataSource

    private val movieTestObserver = TestObserver<List<Movie>>()

    private val movieEntity = MovieApiEntity(
        1,
        arrayListOf(
            MovieDetailsApiEntity(
                false,
                "/aO5ILS7qnqtFIprbJ40zla0jhpu.jpg",
                1,
                "en",
                "Welcome to Sudden Death",
                "some overview",
                1892.824,
                "/elZ6JCzSEvFOq4gNjNeZsnRFsvj.jpg",
                "2020-09-29",
                "Welcome to Sudden Death",
                false,
                6.7,
                 99
            ),
            MovieDetailsApiEntity(
                false,
                "/aO5ILS7qnqtFIprbJ40zla0jhpu.jpg",
                1,
                "en",
                "2067",
                "some overview",
                1892.824,
                "/elZ6JCzSEvFOq4gNjNeZsnRFsvj.jpg",
                "2020-09-29",
                "Enola Holmes",
                false,
                6.8,
                99
            )
        ),
        500,
        1000
    )


    private lateinit var movieDBRepository: MovieDBRepository

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        movieDBRepository = MovieDBRepository(remoteDB, localDB)
    }

    @Test
    fun given_onePageOfPopularMovies_whenGettingMoviesFromRemote_thenReturnMovies() {

        // WHEN we get movies from the database, we expect to receive the value stated
        Mockito.`when`(remoteDB.getMovies(1)).thenReturn(Single.just(movieEntity))

        // GIVEN movies actually requested from the DB
        val actual = remoteDB.getMovies(1).blockingGet()

        // THEN assert that our mocked response is the same as the actual.
        assertEquals(movieEntity.results[0].title, actual.results[0].title)

    }
}