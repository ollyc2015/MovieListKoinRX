package com.oliver_curtis.movies_list.data.db

import androidx.room.*
import com.oliver_curtis.movies_list.data.entity.MovieDetailsEntity
import com.oliver_curtis.movies_list.data.entity.db.MovieDetailsDBEntity
import io.reactivex.Single


@Dao
abstract class MovieDao {
    private companion object {
        const val MOVIE_LIST_ENTITY_NAME = "movie_list"
    }

    @Query("SELECT count(*) FROM timestamps WHERE entity_name = :entityName")
    abstract fun getTimeStampCount(entityName: String): Single<Int>

    @Query("SELECT cache_timestamp FROM timestamps WHERE entity_name = :entityName")
    abstract fun getTimeStamp(entityName: String): Single<Long>

    @Query("SELECT * FROM movies_details")
    abstract fun getMovies(): Single<List<MovieDetailsDBEntity>>

    @Query("DELETE FROM movies_details")
    abstract fun deleteMovies(): Single<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertMovies(movies: List<MovieDetailsDBEntity>) // Cannot return anything from here due to a room-rxjava2 bug.

    @Query("DELETE FROM timestamps WHERE entity_name = :entityName")
    abstract fun deleteTimeStamps(entityName: String): Single<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertTimeStamp(timestamp: TimeStampEntity) // Cannot return anything from here due to a room-rxjava2 bug.


    fun hasMovieCacheTime(): Single<Boolean> = getTimeStampCount(MOVIE_LIST_ENTITY_NAME).map { it == 1 }

    fun getMovieCacheTime(): Single<Long> = getTimeStamp(MOVIE_LIST_ENTITY_NAME)

    @Transaction
    open fun cacheMovies(movies: List<MovieDetailsDBEntity>) {
        deleteMovies()
        insertMovies(movies)

        deleteTimeStamps(MOVIE_LIST_ENTITY_NAME)
        insertTimeStamp(TimeStampEntity(MOVIE_LIST_ENTITY_NAME, System.currentTimeMillis()))
    }
}