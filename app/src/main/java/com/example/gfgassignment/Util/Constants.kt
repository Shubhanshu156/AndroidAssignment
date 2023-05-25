package com.example.gfgassignment.Util

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.example.gfgassignment.R

object Constants {
    val fontfamily= FontFamily(
        Font(R.font.robotomonolight,FontWeight.Light),
        Font(R.font.robotomonobold, FontWeight.Bold),
        Font(R.font.robotomonoextralight, FontWeight.ExtraLight),
        Font(R.font.robotomonomedium, FontWeight.Medium)

    )
    const val BASE_URL="https://api.rss2json.com/"
    const val rssUrl="http://www.abc.net.au/news/feed/51120/rss.xml"

}