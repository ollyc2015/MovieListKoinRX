package com.oliver_curtis.movies_list.data.entity.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.oliver_curtis.movies_list.data.entity.MovieDetailsEntity

@Entity(tableName = "movies_details")
data class MovieDetailsDBEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    override val id: Int,

    @ColumnInfo(name = "adult")
    override val adult: Boolean,

    @ColumnInfo(name = "backdrop_path")
    override val backdrop_path: String?,

    @ColumnInfo(name = "original_language")
    override val original_language: String,

    @ColumnInfo(name = "original_title")
    override val original_title: String,

    @ColumnInfo(name = "overview")
    override val overview: String,

    @ColumnInfo(name = "popularity")
    override val popularity: Double,

    @ColumnInfo(name = "poster_path")
    override val poster_path: String,

    @ColumnInfo(name = "release_date")
    override val release_date: String?,

    @ColumnInfo(name = "title")
    override val title: String,

    @ColumnInfo(name = "video")
    override val video: Boolean,

    @ColumnInfo(name = "vote_average")
    override val vote_average: Double,

    @ColumnInfo(name = "vote_count")
    override val vote_count: Int


) : MovieDetailsEntity {

    constructor(entity: MovieDetailsEntity) : this(
        0,
        entity.adult,
        entity.backdrop_path,
        entity.original_language,
        entity.original_title,
        entity.overview,
        entity.popularity,
        entity.poster_path,
        entity.release_date,
        entity.title,
        entity.video,
        entity.vote_average,
        entity.vote_count
    )
}
