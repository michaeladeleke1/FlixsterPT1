package com.example.project3_flixster

import com.google.gson.annotations.SerializedName

class Movie
{
	@SerializedName("title")
	val movieTitle: String? = null

	@SerializedName("overview")
	val movieDescription: String? = null

	@SerializedName("poster_path")
	val moviePoster: String? = null
}