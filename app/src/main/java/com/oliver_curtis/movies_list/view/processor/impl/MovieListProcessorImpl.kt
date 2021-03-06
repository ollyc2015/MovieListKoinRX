package com.oliver_curtis.movies_list.view.processor.impl

import com.oliver_curtis.movies_list.common.error.DefaultErrorResolver
import com.oliver_curtis.movies_list.common.error.ErrorResolver
import com.oliver_curtis.movies_list.common.viewmodel.CallResult
import com.oliver_curtis.movies_list.domain.model.Movie
import com.oliver_curtis.movies_list.view.MovieListFragment
import com.oliver_curtis.movies_list.view.MovieView
import com.oliver_curtis.movies_list.view.processor.MovieListProcessor

class MovieListProcessorImpl(private val errorResolver: ErrorResolver = DefaultErrorResolver()) : MovieListProcessor {

    private var view: MovieView? = null

    override fun processMovieResponse(callResult: CallResult<List<Movie>>?) {
        if (callResult != null) {

            if (callResult.hasResult()) {
                view?.displayMovies(callResult.result())
            } else if(callResult.hasError()) {
                view?.displayError(errorResolver.findErrorMessageResId(callResult.error()))
            }
        }
    }

    override fun attachView(view: MovieView) {
        this.view = view
    }

    override fun detachView() {
        view = null
    }
}