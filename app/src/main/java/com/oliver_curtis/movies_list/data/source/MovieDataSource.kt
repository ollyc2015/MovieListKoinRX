package com.oliver_curtis.movies_list.data.source

import com.oliver_curtis.movies_list.data.entity.server.MovieApiEntity
import io.reactivex.Single


interface MovieDataSource {

    fun getMovies(page:Int): Single<MovieApiEntity>

}