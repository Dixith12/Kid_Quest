package com.example.kid_quest.screens.homeScreen

import com.example.kid_quest.models.DataorException
import com.example.kid_quest.models.Post

data class UiState(
    var posts: DataorException<List<Post>, Boolean, Exception>? = null,
    var uploadState: Result<Unit>? = null,
    val isUploading: Boolean = false
)