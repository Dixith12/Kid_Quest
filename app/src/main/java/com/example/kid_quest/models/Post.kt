package com.example.kid_quest.models

data class Post(
    val postId: String = "",
    val userId: String = "",
    val description: String = "",
    val username: String = "",
    val userProfileUrl: String = "",
    val timestamp: String = "",
    val imageUrls: List<String> = emptyList(),
    var likes: Int = 0,
    val likedBy: List<String> = emptyList(),
    var isLikedByCurrentUser: Boolean = false

)
