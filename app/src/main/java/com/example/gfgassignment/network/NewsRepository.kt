package com.example.gfgassignment.network

import android.util.Log
import coil.network.HttpException
import com.example.gfgassignment.Database.FeedDatabase
import com.example.gfgassignment.Util.Constants
import com.example.gfgassignment.network.models.FeedItem

import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val Api: ApiService,
    private val db: FeedDatabase
) {

    // Function to fetch the feed data
    suspend fun getFeed(): List<FeedItem> {
        try {
            val resultFromApi = Api.getFeed(Constants.rssUrl)
            if (resultFromApi.isSuccessful && resultFromApi.body() != null) {
                Log.d("in repo", "getFeed: " + resultFromApi.body())

                val resultFormatted = resultFromApi.body()!!.items.map {
                    FeedItem(
                        thumbnail = it.thumbnail,
                        title = it.title,
                        pubDate = it.pubDate,
                        enclosure = it.enclosure.link
                    )
                }

                val dao = db.getFeedDao()
                val existingItems = dao.getfeed() // Fetch existing items from the database

                val itemsToDelete = existingItems.filter { existingItem ->
                    // Check if existing item is present in the new formatted list
                    resultFormatted.any { newItem ->
                        newItem.thumbnail == existingItem.thumbnail &&
                                newItem.title == existingItem.title &&
                                newItem.pubDate == existingItem.pubDate &&
                                newItem.enclosure == existingItem.enclosure
                    }
                }

                // Delete items that exist in the database but not in the new formatted list
                itemsToDelete.forEach { dao.delete(it) }

                // Insert new formatted items into the database
                dao.insertall(resultFormatted)
            }
        }
        catch(e: HttpException) {
            throw Exception(
                "Oops, something went wrong!", // Exception message for HTTP error
            )
        } catch(e: IOException) {
            throw Exception(
                "Couldn't reach server, check your internet connection.", // Exception message for network error
            )
        }
        finally {
            var resultfromdb = db.getFeedDao().getfeed() // Fetch feed data from the database
            return resultfromdb
        }
    }
}
