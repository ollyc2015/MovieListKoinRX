package com.oliver_curtis.movies_list.data.source

import com.oliver_curtis.movies_list.data.entity.MovieDetailsEntity
import io.reactivex.Single

interface MovieCache {

    fun hasMovies(): Single<Boolean>

    fun getMovies(): Single<List<MovieDetailsEntity>>

    fun cacheMovies(movies: List<MovieDetailsEntity>)
}