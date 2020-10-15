package com.oliver_curtis.movies_list.di

import android.app.Application
import com.oliver_curtis.movies_list.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


fun startDependencyInjectionFramework(application: Application) {
    startKoin {
        androidContext(application)
        modules(appModules)
    }
}