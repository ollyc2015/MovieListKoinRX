package com.oliver_curtis.movies_list.data.source.impl

import com.oliver_curtis.movies_list.common.time.DefaultTimeProvider
import com.oliver_curtis.movies_list.common.time.TimeProvider
import com.oliver_curtis.movies_list.data.db.MovieDao
import com.oliver_curtis.movies_list.data.entity.MovieDetailsEntity
import com.oliver_curtis.movies_list.data.entity.db.MovieDetailsDBEntity
import com.oliver_curtis.movies_list.data.source.MovieCache
import io.reactivex.Single

class DBMovieCache(expiryTimeInSecs: Int, private val dao: MovieDao, private val timeProvider: TimeProvider = DefaultTimeProvider()): MovieCache {

    private companion object {
        const val SECS_TO_MILLIS = 1000L
    }

    private val expiryTimeInMillis = expiryTimeInSecs*SECS_TO_MILLIS

    override fun hasMovies(): Single<Boolean> {

        val contentCheck: () -> Single<Boolean> = { dao.hasMovieCacheTime() }
        val expiryCheck: () -> Single<Boolean> = { dao.getMovieCacheTime().map { notExpired(it) } }

        val expiryCheckIfNeeded: (Boolean) -> Single<Boolean> = { contentCheckPassed ->
            if (contentCheckPassed.not()) {
                Single.just(false)
            } else {
                expiryCheck.invoke()
            }
        }

        return contentCheck.invoke().flatMap { expiryCheckIfNeeded.invoke(it) }
    }

    private fun notExpired(timestamp: Long): Boolean {
        return (timeProvider.now() - timestamp <= expiryTimeInMillis)
    }

    override fun getMovies(): Single<List<MovieDetailsEntity>> {
        return dao.getMovies().map {  entities -> entities.map { it } }
    }

    override fun cacheMovies(movies: List<MovieDetailsEntity>) {
        dao.cacheMovies(movies.map { MovieDetailsDBEntity(it) })
    }
}