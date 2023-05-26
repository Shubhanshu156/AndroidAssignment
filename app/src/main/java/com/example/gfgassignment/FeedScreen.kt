package com.example.gfgassignment


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import com.example.gfgassignment.Util.DateFormatter
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState

import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.gfgassignment.Util.Constants
import com.example.gfgassignment.Util.shimmerEffect
import com.example.gfgassignment.network.models.FeedItem
import com.example.gfgassignment.network.models.Item
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import java.time.format.TextStyle

@Preview(showSystemUi = true)
@Composable
fun FeedScreen() {
    // Column composable with top vertical arrangement and background color
    Column(
        verticalArrangement = Arrangement.Top,
        modifier = Modifier.background(Color(228, 228, 228))
    ) {
        // Custom composable to show the action bar
        ShowActionBar("GeeksForGeeks")

        // Custom composable to show an image
        ShowImage()
    }
}


@Composable
fun ShowActionBar(s: String = "GeeksforGeeks") {
    // Row composable for displaying the action bar
    Row(
        modifier = Modifier
            .background(Color.White)
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(top = 15.dp, bottom = 15.dp, start = 15.dp)
    ) {
        // Text composable to display the title
        Text(
            text = s,
            color = Color(71, 149, 71),
            fontFamily = Constants.fontfamily,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable
fun ShowImage(viewmodel: NewsViewModel = hiltViewModel()) {
    // Collect the isLoading state from the NewsViewModel
    val isLoading by viewmodel.isLoading.collectAsStateWithLifecycle()

    // Create a SwipeRefreshState to track the refresh state based on isLoading
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isLoading)

    // Collect the feedlist state from the NewsViewModel
    val res by viewmodel.feedlist.collectAsStateWithLifecycle()

    // Check if the feedlist is not empty or isLoading is true
    if (res.isNotEmpty() || isLoading) {
        // Display data

        // Wrap the content with a SwipeRefresh composable to enable swipe-to-refresh functionality
        SwipeRefresh(state = swipeRefreshState, onRefresh = viewmodel::getfeed) {
            Column {
                // Create a LazyColumn to display the feed items
                LazyColumn(modifier = Modifier) {
                    // Iterate over the feed items using itemsIndexed composable
                    itemsIndexed(res) { index, item ->
                        if (index == 0) {
                            // Display a large image for the first item
                            ShowLargeImage(item)
                        } else {
                            // Display a small image for the rest of the items
                            SmallImageComposable(item)
                        }
                    }
                }
            }
        }
    } else {
        // Display shimmer effect when feedlist is empty and not loading
        ShowShimmerEffect()
    }
}

@Composable
fun ShowShimmerEffect() {
    // Create a LazyColumn to display shimmer effect
    LazyColumn(modifier = Modifier) {
        // Create a mutable list to hold items for the shimmer effect
        val lst: MutableList<Int> = mutableListOf()

        // Add items to the list (in this case, adding integers from 1 to 10)
        for (i in 1..10) {
            lst.add(i)
        }

        // Iterate over the list using itemsIndexed composable
        itemsIndexed(lst) { index, item ->
            if (index == 0) {
                // Display a large image shimmer effect for the first item
                ShowLargeImageShimmer()
            } else {
                // Display a small image shimmer effect for the rest of the items
                SmallImageComposableShimmer()
            }
        }
    }
}

@Composable
fun SmallImageComposableShimmer() {
    // Display a card with shimmer effect for small images
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .clip(RoundedCornerShape(20.dp))
            .height(100.dp)
            .shimmerEffect()
    ) {
        // Empty content lambda, no content to display
    }
}

@Preview
@Composable
fun ShowLargeImageShimmer() {
    // Display a card with shimmer effect for a large image
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .clip(RoundedCornerShape(20.dp))
            .height(300.dp)
            .shimmerEffect()
    ) {
        // Empty content lambda, no content to display
    }
}


@Composable
fun SmallImageComposable(item: FeedItem) {
    // Display a card for a small image
    var componentHeight by remember { mutableStateOf(0.dp) }

    // get local density from composable
    val density = LocalDensity.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(Color.White)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(7f)
                    .background(Color.White)
                    .padding(horizontal = 20.dp, vertical = 20.dp)
                    .onGloballyPositioned {
                        componentHeight = with(density) {
                            it.size.height.toDp()
                        }
                    }
            ) {
                // Display the title of the feed item
                Text(
                    text = item.title,
                    color = Color(71, 149, 71),
                    fontFamily = Constants.fontfamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 18.sp,
                    maxLines = 3,
                    modifier = Modifier.fillMaxHeight()
                )
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(10.dp)
                )

                // Display the formatted date text
                FormattedDateText(inputDate = item.pubDate)
            }
            // Display the small thumbnail image
            AsyncImage(
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .weight(3f)
                    .fillMaxWidth()
                    .height(componentHeight+40.dp),
                model = item.thumbnail,
                contentDescription = ""
            )
        }
    }
}


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ShowLargeImage(item: FeedItem) {
    //initial height set at 0.dp
    var componentHeight by remember { mutableStateOf(0.dp) }

    // get local density from composable
    val density = LocalDensity.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(Color.Gray)
    ) {
        AsyncImage(
            model = item.enclosure,
            contentDescription = "large top image",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxWidth()
                .height(componentHeight+80.dp)
        )

        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(horizontal = 20.dp, vertical = 20.dp)
                .onGloballyPositioned {
                    componentHeight = with(density) {
                        it.size.height.toDp()
                    }
                }
        ) {
            Text(
                text = item.title,
                color = Color(71, 149, 71),
                fontFamily = Constants.fontfamily,
                fontWeight = FontWeight.Medium,
                fontSize = 25.sp,
                letterSpacing = 3.sp,
                maxLines = 3
            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(10.dp)
            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color.LightGray)
                    .padding(horizontal = 5.dp)
            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(10.dp)
            )

            FormattedDateText(inputDate = item.pubDate)
        }
    }
}

@Composable
fun FormattedDateText(inputDate: String) {
    val formattedDate = DateFormatter().formatDate(inputDate)

    val annotatedString = buildAnnotatedString {
        val dateStart = 0
        val dateEnd = formattedDate.indexOf(',') + 6

        append(formattedDate)

        // Add style to the specified range of text
        addStyle(
            style = SpanStyle(fontWeight = FontWeight.Bold, color = Color.Gray),
            start = dateStart,
            end = dateEnd
        )
    }

    Text(
        text = annotatedString,
        color = Color.LightGray,
        fontSize = 10.sp
    )
}
