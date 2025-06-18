package com.example.kid_quest.screens.learning

import com.example.kid_quest.models.VideoModel

data class VideoUiState(
    val videos: List<VideoModel> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
