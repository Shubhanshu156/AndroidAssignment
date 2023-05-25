package com.example.gfgassignment.network.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity("Feed")
data class FeedItem(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val pubDate: String = "",
    val thumbnail: String = "",
    val title: String = "",
    val enclosure: String = ""
)

