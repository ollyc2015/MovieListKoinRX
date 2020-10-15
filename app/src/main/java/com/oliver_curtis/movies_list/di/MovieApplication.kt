package com.oliver_curtis.movies_list.di

import android.app.Application
import com.oliver_curtis.movies_list.di.startDependencyInjectionFramework

class MovieApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startDependencyInjectionFramework(this)
    }
}