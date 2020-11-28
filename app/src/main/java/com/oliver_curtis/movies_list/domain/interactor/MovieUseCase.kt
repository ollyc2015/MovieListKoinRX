package com.oliver_curtis.movies_list.domain.interactor

import com.oliver_curtis.movies_list.domain.model.Movie
import io.reactivex.Single


interface MovieUseCase {

    fun fetchMoviesFromCacheElseRemote(page:Int): Single<List<Movie>?>
    fun fetchMoviesFromRemote(page:Int): Single<List<Movie>?>
}