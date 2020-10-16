package com.oliver_curtis.movies_list.view.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.oliver_curtis.movies_list.common.viewmodel.CallResult
import com.oliver_curtis.movies_list.common.viewmodel.livedata.DefaultLiveDataProvider
import com.oliver_curtis.movies_list.common.viewmodel.livedata.LiveDataProvider
import com.oliver_curtis.movies_list.common.viewmodel.rx.DefaultSchedulerProvider
import com.oliver_curtis.movies_list.common.viewmodel.rx.SchedulerProvider
import com.oliver_curtis.movies_list.domain.interactor.MovieUseCase
import com.oliver_curtis.movies_list.domain.model.Movie
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable

class MovieViewModel(
    private val movieUseCase: MovieUseCase,
    private val liveDataProvider: LiveDataProvider = DefaultLiveDataProvider(),
    private val schedulerProvider: SchedulerProvider = DefaultSchedulerProvider(),
) : ViewModel(){

    fun getMovies(page:Int): MutableLiveData<CallResult<List<Movie>>> {

        return liveDataProvider.liveDataInstance<List<Movie>>().apply {

            movieUseCase.fetchMovies(page)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(object : SingleObserver<List<Movie>?> {
                    override fun onSuccess(t: List<Movie>) {

                        this@apply.postValue(CallResult(t))
                    }

                    override fun onSubscribe(d: Disposable) {}
                    override fun onError(e: Throwable) {

                        this@apply.postValue(CallResult(e))
                    }
                })
        }
    }
}