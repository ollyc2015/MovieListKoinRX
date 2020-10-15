package com.oliver_curtis.movies_list.data.db

import com.oliver_curtis.movies_list.data.entity.MovieApiEntity
import io.reactivex.Single


interface Database {

    fun getMovies(page:Int): Single<MovieApiEntity>

}