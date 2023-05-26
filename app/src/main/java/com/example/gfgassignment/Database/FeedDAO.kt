package com.example.gfgassignment.Database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.example.gfgassignment.network.models.FeedItem

@Dao
interface FeedDAO {
    // Upsert annotation to insert or update a list of FeedItem entities
    @Upsert
    suspend fun insertall(feedlist: List<FeedItem>)

    // Delete annotation to delete a single FeedItem entity
    @Delete
    suspend fun delete(entities: FeedItem)

    // Query annotation to fetch all FeedItem entities from the Feed table sorted by publish date
    @Query("SELECT * FROM Feed")
    suspend fun getfeed(): List<FeedItem>
}
