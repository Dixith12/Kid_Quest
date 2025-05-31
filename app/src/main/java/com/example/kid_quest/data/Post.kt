package com.example.kid_quest.data

data class Post(
    val postId: String = "",
    val userId: String = "",
    val description: String = "",
    val username: String = "",
    val userProfileUrl: String = "",
    val timestamp: String = "",
    val imageUrls: List<String> = emptyList(),
    val likes: Int = 0
)
