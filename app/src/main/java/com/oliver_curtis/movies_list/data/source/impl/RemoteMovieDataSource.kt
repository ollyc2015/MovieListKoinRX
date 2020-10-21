package com.oliver_curtis.movies_list.data.source.impl

import com.oliver_curtis.movies_list.data.api.MovieService
import com.oliver_curtis.movies_list.data.entity.server.MovieApiEntity
import com.oliver_curtis.movies_list.data.source.MovieDataSource
import io.reactivex.Single


class RemoteMovieDataSource(private val service: MovieService) : MovieDataSource {

    override fun getMovies(page:Int): Single<MovieApiEntity> {
        return service.getMovies(page)
    }
}