package com.example.gfgassignment


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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
import com.example.gfgassignment.network.models.Item
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import java.time.format.TextStyle

@Preview(showSystemUi = true)
@Composable
fun FeedScreen() {
    Column(
        verticalArrangement = Arrangement.Top,
        modifier = Modifier.background(Color(228, 228, 228))
    ) {
        ShowActionBar("GeeksForGeeks")
        ShowImage()
    }
}


@Composable
fun ShowActionBar(s: String = "GeeksforGeeks") {
    Row(
        modifier = Modifier
            .background(Color.White)
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(top = 15.dp, bottom = 15.dp, start = 15.dp)
    ) {
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
    val isLoading by viewmodel.isLoading.collectAsStateWithLifecycle()
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isLoading)
    val res by viewmodel.feedlist.collectAsStateWithLifecycle();
    if (res.items.isNotEmpty()) {

            // Display data
             SwipeRefresh(state = swipeRefreshState, onRefresh = viewmodel::getfeed) {
                 Column {

                     if (res.items.isNotEmpty()) {
                         LazyColumn(modifier = Modifier) {
                             itemsIndexed(res.items) { index, item ->
                                 if (index == 0) {
                                     ShowLargeImage(item);
                                 } else {
                                     SmallImageComposable(item)
                                 }
                             }
                         }


                     }
                 }

             }
        }

    }



@Composable
fun SmallImageComposable(item: Item) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(Color.Green)
    ) {
        Row() {
            Column(

                horizontalAlignment = Alignment.Start, modifier = Modifier
                    .weight(7f)
                    .background(Color.White)
                    .padding(horizontal = 20.dp, vertical = 20.dp)
            ) {
                Text(
                    text = item.title, color = Color(71, 149, 71),
                    fontFamily = Constants.fontfamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 18.sp,
                    maxLines = 3

                )
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(10.dp)

                )


                FormattedDateText(inputDate = item.pubDate)
            }
            AsyncImage(
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .weight(3f)
                    .fillMaxHeight()
                    .fillMaxWidth(),
                model = item.thumbnail,
                contentDescription = ""
            )
        }

    }

}


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ShowLargeImage(item: Item) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(Color.Green)
    ) {
        AsyncImage(
            model = item.enclosure.thumbnail, contentDescription = "larrge top image",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .background(Color.Green)

        )

        Column(
            horizontalAlignment = Alignment.Start, modifier = Modifier
                .background(Color.White)
                .padding(horizontal = 20.dp, vertical = 20.dp)
        ) {
            Text(
                text = item.title, color = Color(71, 149, 71),
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
