package com.example.gfgassignment.Util
import java.text.SimpleDateFormat
import java.util.*

class DateFormatter {
    private val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)
    private val outputFormat = SimpleDateFormat("MMM d, yyyy hh:mm a", Locale.US)

    fun formatDate(inputDate: String): String {
        val date = inputFormat.parse(inputDate)
        return outputFormat.format(date)
    }
}
