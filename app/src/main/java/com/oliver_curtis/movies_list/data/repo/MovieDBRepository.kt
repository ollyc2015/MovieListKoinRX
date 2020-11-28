package com.oliver_curtis.movies_list.data.repo

import com.oliver_curtis.movies_list.common.date.formatDate
import com.oliver_curtis.movies_list.common.rx.fetchEntity
import com.oliver_curtis.movies_list.common.rx.readOrFetchEntity
import com.oliver_curtis.movies_list.data.entity.MovieDetailsEntity
import com.oliver_curtis.movies_list.data.source.MovieCache
import com.oliver_curtis.movies_list.data.source.MovieDataSource
import com.oliver_curtis.movies_list.domain.model.Movie
import com.oliver_curtis.movies_list.domain.repo.MovieRepository
import io.reactivex.Single


class MovieDBRepository(
    private val remoteSource: MovieDataSource,
    private val localSource: MovieCache
) : MovieRepository {

    private var movieList = arrayListOf<Movie>()

    override fun getMoviesFromCacheElseRemote(page: Int): Single<List<Movie>?> {
        return readOfFetchMovies(page).map { entity -> toMovie(entity)  }
    }

    private fun readOfFetchMovies(page: Int): Single<List<MovieDetailsEntity>> {
        val hasMovies = { localSource.hasMovies() }
        val readMovies = { localSource.getMovies() }
        val fetchMovies = { fetchValidMovies(page) }
        val cacheMovies = { movies: List<MovieDetailsEntity> -> localSource.cacheMovies(movies) }

        return readOrFetchEntity(hasMovies, readMovies, fetchMovies, cacheMovies)
    }

    override fun fetchValidMoviesFromRemote(page: Int): Single<List<Movie>?> {

        val fetchMovies = { fetchValidMovies(page) }
        val cacheMovies = { movies: List<MovieDetailsEntity> -> localSource.cacheMovies(movies) }

        return fetchEntity(fetchMovies, cacheMovies).map { entity -> toMovie(entity) }
    }

    private fun fetchValidMovies(page: Int): Single<List<MovieDetailsEntity>> {
        return remoteSource.getMovies(page).map { it.results }
    }


    private fun toMovie(entity: List<MovieDetailsEntity>): List<Movie> {

        entity.forEach {

            val id = it.id

            val posterPath = it.poster_path

            val title = it.title

            val votingAverage = it.vote_average

            val releaseDate = it.release_date
            if (!releaseDate.isNullOrBlank()) {
                val date = formatDate(releaseDate)
                movieList.add(Movie(id, posterPath, title, votingAverage, date))
            }else{
                movieList.add(Movie(id, posterPath, title, votingAverage, ""))
            }
        }

        return movieList

    }
}
