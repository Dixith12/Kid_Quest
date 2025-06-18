package com.example.kid_quest.screens.learning

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.kid_quest.components.SurfaceColor
import com.example.kid_quest.components.TopAppBar
import com.example.kid_quest.models.VideoModel
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import coil.request.ImageRequest

@Composable
fun VideosList(navController: NavController,
               viewModel: LearningViewModel = hiltViewModel(),
                classLevel: Int) {
    val state by viewModel.videoUiState.collectAsState()
    Log.d("DebugTest", "VideosList Composable Entered")

    LaunchedEffect(Unit) {
        viewModel.fetchVideos(classLevel)
    }

    Scaffold(
        topBar = {
            TopAppBar(name = "Class $classLevel",
                showBack = true,
                onBack = {
                    navController.popBackStack()
                })
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        SurfaceColor(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (state.isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
                VideosContainer(videos = state.videos)
            }
        }
    }
}

@Composable
fun VideosContainer(videos: List<VideoModel>) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
            .verticalScroll(rememberScrollState())
    ) {
        videos.forEach { video ->
            VideoCard(video)
        }
    }
}

@Composable
fun VideoCard(video: VideoModel) {
    var isPlaying by remember { mutableStateOf(false) }
    val lifecycleOwner = LocalLifecycleOwner.current

    val context = LocalContext.current
    val thumbnailUrl = "https://img.youtube.com/vi/${video.videoId}/maxresdefault.jpg"
    val fallbackUrl = "https://img.youtube.com/vi/${video.videoId}/0.jpg"

    var imageUrl by remember { mutableStateOf(thumbnailUrl) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (isPlaying) {
                AndroidView(
                    factory = { context ->
                        val youTubePlayerView = YouTubePlayerView(context)
                        lifecycleOwner.lifecycle.addObserver(youTubePlayerView)

                        youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                            override fun onReady(youTubePlayer: YouTubePlayer) {
                                youTubePlayer.loadVideo(video.videoId, 0f)
                            }
                        })

                        youTubePlayerView
                    },
                    modifier = Modifier
                        .height(200.dp)
                        .fillMaxWidth()
                )
            } else {
                Box(
                    modifier = Modifier
                        .height(200.dp)
                        .fillMaxWidth()
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(context)
                            .data(imageUrl)
                            .crossfade(true)
                            .listener(
                                onError = { _, _ ->
                                    // Switch to fallback URL if loading fails
                                    if (imageUrl != fallbackUrl) {
                                        imageUrl = fallbackUrl
                                    }
                                }
                            )
                            .build(),
                        contentDescription = "Video thumbnail",
                        modifier = Modifier.fillMaxSize()
                    )


                    IconButton(
                        onClick = { isPlaying = true },
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(64.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = "Play",
                            tint = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.85f),
                            modifier = Modifier.size(64.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = video.title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(8.dp),
                color = Color.Black,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
        }
    }
}
