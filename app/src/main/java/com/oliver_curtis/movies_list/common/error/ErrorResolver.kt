package com.oliver_curtis.movies_list.common.error

interface ErrorResolver {

    fun findErrorMessageResId(error: Throwable): Int
}