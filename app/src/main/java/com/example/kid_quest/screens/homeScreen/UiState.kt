package com.example.kid_quest.screens.homeScreen

import androidx.compose.runtime.mutableStateOf
import com.example.kid_quest.data.DataorException
import com.example.kid_quest.data.Post

data class UiState(
    var posts: DataorException<List<Post>, Boolean, Exception>? = null,
    var uploadState: Result<Unit>? = null,
    val isUploading: Boolean = false
)