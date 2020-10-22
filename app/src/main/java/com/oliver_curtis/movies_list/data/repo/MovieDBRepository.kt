package com.oliver_curtis.movies_list.data.repo

import com.oliver_curtis.movies_list.common.date.formatDate
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

    private val movieList: MutableList<Movie>? = arrayListOf()

    override fun getMovies(page: Int): Single<List<Movie>?> {
        return readOfFetchMovies(page).map { entity -> entity.map { toMovie(it) } }
    }

    private fun readOfFetchMovies(page: Int): Single<List<MovieDetailsEntity>> {
        val hasMovies = { localSource.hasMovies() }
        val readMovies = { localSource.getMovies() }
        val fetchLaunches = { fetchValidLaunches(page) }
        val cacheMovies = { movies: List<MovieDetailsEntity> -> localSource.cacheMovies(movies) }

        return readOrFetchEntity(hasMovies, readMovies, fetchLaunches, cacheMovies)
    }

    private fun fetchValidLaunches(page: Int): Single<List<MovieDetailsEntity>> {
        return remoteSource.getMovies(page).map { it.results }
    }


    private fun toMovie(entity: MovieDetailsEntity): Movie {

        // movieList?.clear()

        //entity.forEach {

        val id = entity.id

        val posterPath = entity.poster_path

        val title = entity.title

        val votingAverage = entity.vote_average

        val releaseDate = entity.release_date
        val date = formatDate(releaseDate)

        return Movie(id, posterPath, title, votingAverage, date)

        // movieList?.add(Movie(id, posterPath, title, votingAverage, date))

        // }
        //return movieList?.toList()
    }
}
