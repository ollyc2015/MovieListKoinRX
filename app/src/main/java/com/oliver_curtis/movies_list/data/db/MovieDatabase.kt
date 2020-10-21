package com.oliver_curtis.movies_list.data.db

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.oliver_curtis.movies_list.data.entity.db.MovieDetailsDBEntity

@Database(entities = arrayOf(MovieDetailsDBEntity::class, TimeStampEntity::class), version = 1)
abstract class MovieDatabase : RoomDatabase() {
    companion object {
        private const val LAUNCHES_DB_NAME = "movie-db"

        @Volatile
        private var INSTANCE: MovieDatabase? = null

        /**
         * Accepting Application instances to prevent the accidental passing of a non-application context.
         */
        fun singleInstance(app: Application): MovieDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: build(app, LAUNCHES_DB_NAME).also { INSTANCE = it }
            }
        }

        private fun build(app: Application, dbName: String): MovieDatabase {
            return Room.databaseBuilder(app, MovieDatabase::class.java, dbName).build()
        }

        fun closeInstance() {
            if (INSTANCE?.isOpen == true) {
                INSTANCE?.close()
            }

            INSTANCE = null
        }
    }

    abstract fun movieDao(): MovieDao
}