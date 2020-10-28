package com.oliver_curtis.movies_list.domain.model

//data class used to simply hold data. Using a data class allows us to automatically have access to
//toString, equals (object equality), componentN() (corresponds to the properties in their order of declaration)
//and copy (make a reference to the original copied object)
data class Movie(
     val id: Int,
     val poster_path: String,
     val title: String,
     val voting_average: Double,
     val release_date: String
)