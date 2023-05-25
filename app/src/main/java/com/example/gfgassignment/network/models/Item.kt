package com.example.gfgassignment.network.models

data class Item(
    val author: String="",
    val categories: List<String> = emptyList(),
    val content: String="",
    val description: String="",
    val enclosure: Enclosure=Enclosure(),
    val guid: String="",
    val link: String="",
    val pubDate: String="",
    val thumbnail: String="",
    val title: String=""

)