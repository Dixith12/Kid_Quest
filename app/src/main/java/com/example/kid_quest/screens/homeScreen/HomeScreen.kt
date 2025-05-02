package com.example.kid_quest.screens.homeScreen

import BottomNav
import android.annotation.SuppressLint
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.kid_quest.R
import com.example.kid_quest.components.TopAppBar
import com.example.kid_quest.data.Post
import com.example.kid_quest.navigation.Screens

@SuppressLint("AutoboxingStateCreation")
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    var itemss = listOf(
        Post(
            name = "Deekshith",
            profileImage = R.drawable.profile,
            images = listOf(R.drawable.image, R.drawable.image, R.drawable.image),
            description = "Experimind Start your countdowmn to the glorious arrival of experience fest #Experimind",
            count = 112,
            timestamp = "10:00 AM Sun,Oct 6 2024"
        ),
        Post(
            name = "Deekshith",
            profileImage = R.drawable.profile,
            images = listOf(R.drawable.image, R.drawable.image, R.drawable.image),
            description = "Experimind Start your countdowmn to the glorious arrival of experience fest #Experimind",
            count = 112,
            timestamp = "10:00 AM Sun,Oct 6 2024"
        ),
        Post(
            name = "Deekshith",
            profileImage = R.drawable.profile,
            images = listOf(R.drawable.image, R.drawable.image, R.drawable.image),
            description = "Experimind Start your countdowmn to the glorious arrival of experience fest #Experimind",
            count = 112,
            timestamp = "10:00 AM Sun,Oct 6 2024"
        )
    )

    val posts = viewModel.posts.collectAsStateWithLifecycle().value
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            TopAppBar(
                name = "Kid_Quest",
                icon = R.drawable.message,
                onclick = {
                    navController.navigate(Screens.PostScreen.route)
                })
        },
        bottomBar = {
            BottomNav(navController)
        },
//        floatingActionButton = {
//            BottomBar(navController)
//        },
//        floatingActionButtonPosition = FabPosition.Center,
        containerColor = Color.White,
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            color = Color.White
        ) {
            if (posts.loading == true) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                )
                {
                    CircularProgressIndicator(color = Color.Black)
                }
            } else {
                HomeContent(
                    itemss,
                    posts = posts.data,
                )
            }
        }
    }
}


@Composable
fun HomeContent(items: List<Post>, posts: List<Post>?) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
//            .padding(15.dp)
    ) {
//        if (posts != null) {
//            items(items = posts)
//            { item ->
//                HomePost(item)
//            }
//        }
        items(items){
            item->
            HomePost(item)
        }
    }
}

@Composable
fun HomePost(item: Post) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
//        modifier = Modifier.padding(5.dp)
    )
    {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 5.dp)
        )
        {
            Image(
                painter = painterResource(id = R.drawable.profile),
                contentDescription = null,
                modifier = Modifier.size(50.dp)
            )
            Text(
                text = item.name,
                color = Color.Black,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }
        PostContent(item)
        Spacer(modifier = Modifier.height(8.dp))
    }
    HorizontalDivider()
}

@Composable

fun PostContent(item: Post) {
    val pagerState = rememberPagerState(pageCount = { item.images.size })


    Card(
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(Color.White),
        elevation = CardDefaults.cardElevation(8.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp)
    )
    {
        Column(modifier = Modifier)
        {
            HorizontalPager(pagerState)
            { page ->
                Image(
                    painter = painterResource(id = item.images[page]),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .height(350.dp)
                        .fillMaxWidth()
                        .padding(15.dp)
                        .clip(RoundedCornerShape(10.dp))
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                repeat(item.images.size) {
                    Indicator(isActive = it == pagerState.currentPage)
                }
            }
            ExpandableText(text = item.description)
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp, end = 15.dp, bottom = 15.dp)
            )
            {
                Row(verticalAlignment = Alignment.CenterVertically)
                {
                    Icon(
                        imageVector = Icons.Filled.FavoriteBorder,
                        contentDescription = "",
                        tint = Color(0xFF312C2C),
                        modifier = Modifier.padding(end = 10.dp)
                    )
                    Text(
                        text = "${item.count}",
                        modifier = Modifier.padding(end = 4.dp),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF312C2C)
                    )
                    Text(
                        text = "Likes",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF312C2C)
                    )
                }
                Text(
                    text = item.timestamp,
                    color = Color(0xFF312C2C)
                )
            }

        }
    }
}

@Composable
fun Indicator(isActive: Boolean = true) {
    Box(
        modifier = Modifier
            .width(if (isActive) 30.dp else 15.dp)
            .height(15.dp)
            .padding(3.dp)
            .background(color = if (isActive) Color.Blue else Color.Gray, shape = CircleShape)
    )
}

@Composable
fun ExpandableText(
    text: String,
    minimizedMaxLines: Int = 2
) {
    var isExpanded by remember { mutableStateOf(false) }
    var isTextOverflowing by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp)
            .animateContentSize()
    ) {
        Text(
            text = buildAnnotatedString {
                append(text)

                if (isTextOverflowing || isExpanded) {
                    withStyle(style = SpanStyle(color = Color.Blue, fontWeight = FontWeight.Bold)) {
                        append(if (isExpanded) " less" else "more")
                    }
                }
            },
            maxLines = if (isExpanded) Int.MAX_VALUE else minimizedMaxLines,
            overflow = TextOverflow.Ellipsis,
            color = Color(0xFF312C2C),
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            onTextLayout = { layoutResult ->
                if (!isExpanded) {
                    isTextOverflowing = layoutResult.hasVisualOverflow
                }
            },
            modifier = Modifier.clickable {
                if (isTextOverflowing || isExpanded) {
                    isExpanded = !isExpanded
                }
            }
        )
    }
}


