package com.example.gfgassignment.Database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.gfgassignment.network.models.FeedItem

@Database(entities = [FeedItem::class], version = 1)
abstract class FeedDatabase : RoomDatabase() {
    // Abstract function to retrieve the FeedDAO
    abstract fun getFeedDao(): FeedDAO
}
