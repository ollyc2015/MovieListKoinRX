package com.oliver_curtis.movies_list.common.time

import com.oliver_curtis.movies_list.common.time.TimeProvider

class DefaultTimeProvider : TimeProvider {

    override fun now(): Long = System.currentTimeMillis()
}