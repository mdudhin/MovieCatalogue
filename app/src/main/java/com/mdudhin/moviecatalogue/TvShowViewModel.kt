package com.mdudhin.moviecatalogue

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.mdudhin.moviecatalogue.entity.TvShow
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class TvShowViewModel : ViewModel() {

    companion object{
        private const val API_KEY = "2cf7c312eac4c11871f8f47cf7bc9df5"
    }

    val listTvShow = MutableLiveData<ArrayList<TvShow>>()

    internal fun setTvShow() {
        val client = AsyncHttpClient()
        val listItems = ArrayList<TvShow>()
        val url = "https://api.themoviedb.org/3/discover/tv?api_key=$API_KEY&language=en-US"

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
                        val tvShow = list.getJSONObject(i)
                        val tvShowItems =
                            TvShow()

                        tvShowItems.id = tvShow.getInt("id")
                        tvShowItems.poster_path = tvShow.getString("poster_path")
                        tvShowItems.name = tvShow.getString("name")
                        tvShowItems.first_air_date = tvShow.getString("first_air_date")
                        tvShowItems.vote_average = tvShow.getDouble("vote_average")
                        tvShowItems.original_language = tvShow.getString("original_language")
                        tvShowItems.overview = tvShow.getString("overview")
                        tvShowItems.backdrop_path = tvShow.getString("backdrop_path")
                        listItems.add(tvShowItems)
                    }
                    listTvShow.postValue(listItems)
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

    internal fun getTvShow(): LiveData<ArrayList<TvShow>> {
        return listTvShow
    }
}