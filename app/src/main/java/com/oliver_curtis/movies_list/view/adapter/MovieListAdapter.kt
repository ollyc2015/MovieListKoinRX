package com.oliver_curtis.movies_list.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.oliver_curtis.movies_list.R
import com.oliver_curtis.movies_list.domain.model.Movie


class MovieListAdapter(private val movies: MutableList<Movie>): RecyclerView.Adapter<MovieViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    //Using viewholder pattern to reduce number of findViewById calls, as this is an expensive call.
    //By using the viewholder pattern a created object will hold references to the sub-views after you "find" them
    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bindLaunch(movies[position])
    }

    //We use a DiffUtil to check if any changes have been detected in the data in the recylerview
    //If changes have been made, update the view. The alternative is to use notifyDataSetChanged()
    //which is an expensive call, as we redraw all the views.
    fun updateAll(updated: List<Movie>) {
        val diffResult = DiffUtil.calculateDiff(MovieDiff(movies, updated), false)
        movies.clear()
        movies.addAll(updated)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun getItemCount(): Int {
        return movies.size
    }
}