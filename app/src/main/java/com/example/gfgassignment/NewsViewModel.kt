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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


@HiltViewModel
class NewsViewModel @Inject constructor(private  val repository: NewsRepository):ViewModel() {
    private val _isLoading = MutableStateFlow(false)
    val isLoading=_isLoading.asStateFlow()
    private var _feedlist: MutableStateFlow<NewsResponse> =
        MutableStateFlow(NewsResponse())
    var feedlist = _feedlist.asStateFlow()
    init {

        getfeed()
    }

     fun getfeed() {
         Log.d("inside viewmodel", "getfeed: "+"new api call")
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value=true
            val  data=repository.getFeed()
            _feedlist.value=data
            _isLoading.value=false


        }

    }
}