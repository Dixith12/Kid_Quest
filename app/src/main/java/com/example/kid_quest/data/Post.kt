package com.example.kid_quest.data

data class Post(
    val postId: String="",
    val uid:String="",
    val name: String="",
    val profileImage: Int?=null,
    val images: List<Int>,
    val description: String,
    val count: Int= 0,
    val timestamp: String
)

