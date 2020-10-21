package com.oliver_curtis.movies_list.data.entity.server

import com.oliver_curtis.movies_list.data.entity.MovieDetailsEntity

data class MovieDetailsApiEntity(
    override val adult: Boolean,
    override val backdrop_path: String,
    override val id: Int,
    override val original_language: String,
    override val original_title: String,
    override val overview: String,
    override val popularity: Double,
    override val poster_path: String,
    override val release_date: String,
    override val title: String,
    override val video: Boolean,
    override val vote_average: Double,
    override val vote_count: Int,
): MovieDetailsEntity