package com.example.project3_flixster

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.project3_flixster.R.id

class MoviesRecyclerViewAdapter(
	private val movies: List<Movie>,
	private val mListener: OnListFragmentInteractionListener?
) : RecyclerView.Adapter<MoviesRecyclerViewAdapter.MovieViewHolder>()
{
	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder
	{
		val view = LayoutInflater.from(parent.context)
			.inflate(R.layout.fragment_movie, parent, false)
		return MovieViewHolder(view)
	}

	inner class MovieViewHolder(val mView: View) : RecyclerView.ViewHolder(mView)
	{
		var movie: Movie? = null
		val movieTitle: TextView = mView.findViewById<View>(id.movie_title) as TextView
		val movieDescription: TextView = mView.findViewById<View>(id.movie_description) as TextView
		val moviePoster: ImageView = mView.findViewById<View>(id.movie_poster) as ImageView

		override fun toString(): String
		{
			return "$movieTitle: '$movieDescription'"
		}
	}

	override fun onBindViewHolder(holder: MovieViewHolder, position: Int)
	{
		val movie = movies[position]

		holder.movie = movie
		holder.movieTitle.text = movie.movieTitle
		holder.movieDescription.text = movie.movieDescription

		Glide.with(holder.mView)
			.load("https://image.tmdb.org/t/p/w500" + movie.moviePoster)
			.centerInside()
			.into(holder.moviePoster)

		holder.mView.setOnClickListener {
			holder.movie?.let { movie ->
				mListener?.onItemClick(movie)
			}
		}
	}

	override fun getItemCount(): Int
	{
		return movies.size
	}
}