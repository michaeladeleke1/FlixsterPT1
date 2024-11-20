package com.example.project3_flixster

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.RequestParams
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.Headers

private const val API_KEY = "a07e22bc18f5cb106bfe4cc1f83ad8ed"

class MoviesFragment : Fragment(), OnListFragmentInteractionListener
{
	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View?
	{
		val view = inflater.inflate(R.layout.fragment_movies_list, container, false)
		val progressBar = view.findViewById<View>(R.id.progress_bar) as ProgressBar
		val recyclerView = view.findViewById<View>(R.id.moviesRv) as RecyclerView
		val context = view.context
		recyclerView.layoutManager = GridLayoutManager(context, 1)
		updateAdapter(progressBar, recyclerView)
		return view
	}

	private fun updateAdapter(progressBar: ProgressBar, recyclerView: RecyclerView)
	{
		val client = AsyncHttpClient()
		val params = RequestParams()
		params["api-key"] = API_KEY

		client[
				"https://api.themoviedb.org/3/movie/now_playing?api_key=$API_KEY",
				params,
				object : JsonHttpResponseHandler()
				{
					override fun onSuccess(
						statusCode: Int,
						headers: Headers,
						json: JsonHttpResponseHandler.JSON
					)
					{
						progressBar.visibility = View.GONE

						val resultsJSON: String = json.jsonObject.get("results").toString()

						val gson = Gson()
						val arrayMovieType = object: TypeToken<List<Movie>>() {}.type
						val models: List<Movie> = gson.fromJson(resultsJSON, arrayMovieType)
						recyclerView.adapter = MoviesRecyclerViewAdapter(models, this@MoviesFragment)

						Log.d("MoviesFragment", "response successful")
					}

					override fun onFailure(
						statusCode: Int,
						headers: Headers?,
						errorResponse: String,
						t: Throwable?
					)
					{
						progressBar.visibility = View.GONE

						t?.message?.let {
							Log.e("MoviesFragment", errorResponse)
						}
					}
				}]
	}

	override fun onItemClick(item: Movie)
	{
		Toast.makeText(context, "${item.movieTitle}", Toast.LENGTH_SHORT).show()
	}
}