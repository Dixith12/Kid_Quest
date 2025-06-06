package com.example.kid_quest.screens.profile

import com.example.kid_quest.data.User

data class UiState(
    var user: User? = null,
    val isUploading: Boolean = false
)
