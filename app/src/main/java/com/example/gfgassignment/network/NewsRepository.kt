package com.example.gfgassignment.network

import android.util.Log
import com.example.gfgassignment.Util.Constants.rssUrl
import com.example.gfgassignment.network.models.NewsResponse

import retrofit2.Response
import javax.inject.Inject

class NewsRepository @Inject constructor(private val Api: ApiService) {

     suspend fun getFeed(): NewsResponse {
        var result:Response<NewsResponse> = Api.getFeed(rssUrl)
        if (result.isSuccessful && result.body()!=null) {
         Log.d("in repo", "getFeed: "+result.body())
            return result.body()!!
        }
        else{
            throw Exception("Error fetching concepts: ${result.message()}")
        }
    }

}