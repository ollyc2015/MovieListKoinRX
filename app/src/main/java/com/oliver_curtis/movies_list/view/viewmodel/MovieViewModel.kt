package com.oliver_curtis.movies_list.view.viewmodel

import android.util.Log
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

    fun getMoviesFromCacheElseRemote(page:Int): MutableLiveData<CallResult<List<Movie>>> {

        //return MutableLiveData<CallResult<T>> by passing our List<movie> (our T) then use apply to configure our object
        //in this case, update our List<Movie> with the value returned from the server.
            return liveDataProvider.liveDataInstance<List<Movie>>().apply {

            movieUseCase.fetchMoviesFromCacheElseRemote(page)
                .subscribeOn(schedulerProvider.io()) //It is used for non CPU-intensive I/O type work including interaction with the file system, performing network calls, database interactions, etc.
                .observeOn(schedulerProvider.ui()) //observe on the main thread
                .subscribe(object : SingleObserver<List<Movie>?> {
                    override fun onSuccess(t: List<Movie>) {

                        this@apply.postValue(CallResult(t))
                    }
                    override fun onSubscribe(d: Disposable) {}//onSubscribe: will be called whenever an observer is subscribed. which we are not using
                    override fun onError(e: Throwable) {

                        this@apply.postValue(CallResult(e))
                    }
                })
        }
    }

    fun getMoviesFromRemote(page:Int): MutableLiveData<CallResult<List<Movie>>> {

        //return MutableLiveData<CallResult<T>> by passing our List<movie> (our T) then use apply to configure our object
        //in this case, update our List<Movie> with the value returned from the server.
        return liveDataProvider.liveDataInstance<List<Movie>>().apply {

            movieUseCase.fetchMoviesFromRemote(page)
                .subscribeOn(schedulerProvider.io()) //It is used for non CPU-intensive I/O type work including interaction with the file system, performing network calls, database interactions, etc.
                .observeOn(schedulerProvider.ui()) //observe on the main thread
                .subscribe(object : SingleObserver<List<Movie>?> {
                    override fun onSuccess(t: List<Movie>) {

                        this@apply.postValue(CallResult(t))
                    }
                    override fun onSubscribe(d: Disposable) {}//onSubscribe: will be called whenever an observer is subscribed. which we are not using
                    override fun onError(e: Throwable) {

                        this@apply.postValue(CallResult(e))
                    }
                })
        }
    }
}