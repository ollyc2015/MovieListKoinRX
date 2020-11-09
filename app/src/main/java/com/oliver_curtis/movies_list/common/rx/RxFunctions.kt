package com.oliver_curtis.movies_list.common.rx

import io.reactivex.Single

/**
 * Helper function that checks if an entity is cached, and returns it from the cache if so, or fetches
 * it from a remote source, and caches it before returning it.
 */
fun <T> readOrFetchEntity(
    isCached: () -> Single<Boolean>,
    readFromCache: () -> Single<T>,
    fetch: () -> Single<T>,
    cache: (T) -> Unit
): Single<T> {
    val readOrFetch: (Boolean) -> Single<T> = { cached ->
        if (cached) {
            readFromCache.invoke()
        } else {
            //fetch movies and then pass the movies returned (it) to the cacheEntity method, as well as the
            //value 'cache', so that when passed to the cacheEntity method, we can pass the value of entity to the cache
            //and then return it (the List<MovieDetailsEntity> in our case)
            fetch.invoke().map { cacheEntity(it, cache) }
        }
    }

    //Return results as one collection
    return isCached.invoke().flatMap(readOrFetch)
}

private fun <T> cacheEntity(entity: T, cache: (T) -> Unit): T {
    return entity.apply { cache.invoke(this) }
}