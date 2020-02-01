package com.mdudhin.moviecatalogue.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.mdudhin.moviecatalogue.entity.Movie
import cz.msebera.android.httpclient.Header
import org.json.JSONObject
import java.lang.Exception

class MovieViewModel : ViewModel() {

    companion object{
        private const val API_KEY = "2cf7c312eac4c11871f8f47cf7bc9df5"
    }

    val listMovie = MutableLiveData<ArrayList<Movie>>()

    internal fun setMovie() {
        val client = AsyncHttpClient()
        val listItems = ArrayList<Movie>()
        val url = "https://api.themoviedb.org/3/discover/movie?api_key=$API_KEY&language=en-US"

        client.get(url, object : AsyncHttpResponseHandler(){
            override fun onSuccess(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray
            ) {
                try {
                    val result = String(responseBody)
                    val responseObject = JSONObject(result)
                    val list = responseObject.getJSONArray("results")

                    for (i in 0 until list.length()){
                        val movie = list.getJSONObject(i)
                        val movieItems =
                            Movie()

                        movieItems.id = movie.getInt("id")
                        movieItems.poster_path = movie.getString("poster_path")
                        movieItems.title = movie.getString("title")
                        movieItems.release_date = movie.getString("release_date")
                        movieItems.vote_average = movie.getDouble("vote_average")
                        movieItems.original_language = movie.getString("original_language")
                        movieItems.overview = movie.getString("overview")
                        movieItems.backdrop_path = movie.getString("backdrop_path")
                        listItems.add(movieItems)
                    }
                    listMovie.postValue(listItems)
                } catch (e: Exception){
                    Log.d("Exception", e.message.toString())
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray,
                error: Throwable
            ) {
                Log.d("onFailure", error.message.toString())
            }

        })
    }

    internal fun getMovies(): LiveData<ArrayList<Movie>> {
        return listMovie
    }

}