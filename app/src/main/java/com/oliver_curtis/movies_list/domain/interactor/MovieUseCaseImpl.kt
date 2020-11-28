package com.oliver_curtis.movies_list.domain.interactor

import com.oliver_curtis.movies_list.domain.model.Movie
import com.oliver_curtis.movies_list.domain.repo.MovieRepository
import io.reactivex.Single


class MovieUseCaseImpl(private val repository: MovieRepository) : MovieUseCase {
    override fun fetchMoviesFromCacheElseRemote(page:Int): Single<List<Movie>?> = repository.getMoviesFromCacheElseRemote(page)
    override fun fetchMoviesFromRemote(page: Int): Single<List<Movie>?> = repository.fetchValidMoviesFromRemote(page)

}