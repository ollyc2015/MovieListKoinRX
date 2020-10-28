package com.oliver_curtis.movies_list.di

import android.app.Application
import com.oliver_curtis.movies_list.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

/*
Benefits

It's easier to swap out implementations of a dependency. Classes no longer control how their dependencies are created,
but instead work with any configuration.

Ease of testing: A class doesn't manage its dependencies, so when you're testing it,
you can pass in different implementations to test all of your different cases.
*/

fun startDependencyInjectionFramework(application: Application) {
    startKoin {
        androidContext(application)
        modules(appModules)
    }
}