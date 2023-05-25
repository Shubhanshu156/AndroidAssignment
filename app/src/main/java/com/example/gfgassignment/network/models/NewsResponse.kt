package com.example.gfgassignment.network.models

data class NewsResponse(
    val feed: Feed = Feed(),
    val items: List<Item> = emptyList(),
    val status: String = ""
)
