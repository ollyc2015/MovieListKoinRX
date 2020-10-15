package com.oliver_curtis.movies_list.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.oliver_curtis.movies_list.BuildConfig
import com.oliver_curtis.movies_list.common.retrofit.retrofitInstance
import com.oliver_curtis.movies_list.data.api.MovieService
import com.oliver_curtis.movies_list.data.db.MovieDatabase
import com.oliver_curtis.movies_list.data.repo.MovieDBRepository
import com.oliver_curtis.movies_list.domain.interactor.MovieUseCase
import com.oliver_curtis.movies_list.domain.interactor.MovieUseCaseImpl
import com.oliver_curtis.movies_list.domain.repo.MovieRepository
import com.oliver_curtis.movies_list.view.processor.MovieListProcessor
import com.oliver_curtis.movies_list.view.processor.impl.MovieListProcessorImpl
import com.oliver_curtis.movies_list.view.viewmodel.MovieViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val appModules = module{

    single<Gson> { GsonBuilder().setLenient().create() }

    single { retrofitInstance(BuildConfig.BASE_URL, get()) }

    single { (get() as Retrofit).create(MovieService::class.java) }

    single { MovieDatabase(get()) }

    factory<MovieRepository>{ MovieDBRepository(get()) }

    single<MovieUseCase> { MovieUseCaseImpl(get())  }

    viewModel { MovieViewModel(get()) }

    factory <MovieListProcessor>{ MovieListProcessorImpl()}
}