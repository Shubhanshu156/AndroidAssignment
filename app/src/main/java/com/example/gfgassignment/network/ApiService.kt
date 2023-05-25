package com.example.gfgassignment.network

import com.example.gfgassignment.network.models.NewsResponse
import retrofit2.http.GET

import retrofit2.Call
import retrofit2.Response

import retrofit2.http.Query

interface ApiService {
    @GET("v1/api.json")
    suspend fun getFeed(@Query("rss_url") rssUrl: String):Response<NewsResponse>
}
