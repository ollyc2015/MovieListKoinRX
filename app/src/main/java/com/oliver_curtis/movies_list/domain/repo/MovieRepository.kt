package com.oliver_curtis.movies_list.domain.repo

import com.oliver_curtis.movies_list.data.entity.MovieDetailsEntity
import com.oliver_curtis.movies_list.domain.model.Movie
import io.reactivex.Single


interface MovieRepository {

    fun getMoviesFromCacheElseRemote(page:Int): Single<List<Movie>?>
    fun fetchValidMoviesFromRemote(page: Int): Single<List<Movie>?>
}