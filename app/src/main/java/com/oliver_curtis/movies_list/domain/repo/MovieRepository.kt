package com.oliver_curtis.movies_list.domain.repo

import com.oliver_curtis.movies_list.domain.model.Movie
import io.reactivex.Single


interface MovieRepository {

    fun getMovies(page:Int): Single<List<Movie>?>
}