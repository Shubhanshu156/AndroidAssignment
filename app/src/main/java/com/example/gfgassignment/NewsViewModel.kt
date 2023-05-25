package com.example.gfgassignment

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.gfgassignment.network.NewsRepository
import com.example.gfgassignment.network.models.NewsResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import com.example.gfgassignment.network.models.FeedItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class NewsViewModel @Inject constructor(private val repository: NewsRepository) : ViewModel() {
    // Flag to denote if the data is in loading phase or not
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    // StateFlow to store data Feed Result
    private var _feedlist: MutableStateFlow<List<FeedItem>> =
        MutableStateFlow(listOf<FeedItem>())
    var feedlist = _feedlist.asStateFlow()

    init {
        // Call the getfeed function when the NewsViewModel is initialized
        getfeed()
    }

    // Function to fetch the feed data
    fun getfeed() {
        Log.d("inside viewmodel", "getfeed: " + "new api call")
        // Execute the API call within a coroutine on the IO dispatcher
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true // Set isLoading to true to indicate loading phase
            val data = repository.getFeed() // Call the repository function to fetch the feed data
            _feedlist.value = data // Update the feedlist with the fetched data
            _isLoading.value = false // Set isLoading to false to indicate the end of loading phase
        }
    }
}
