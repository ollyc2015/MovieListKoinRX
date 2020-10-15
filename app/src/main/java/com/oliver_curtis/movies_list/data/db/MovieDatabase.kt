package com.oliver_curtis.movies_list.data.db

import com.oliver_curtis.movies_list.data.api.MovieService
import com.oliver_curtis.movies_list.data.entity.MovieApiEntity
import io.reactivex.Single


class MovieDatabase(private val service: MovieService) : Database {

    override fun getMovies(page:Int): Single<MovieApiEntity> {
        return service.getMovies(page)
    }
}