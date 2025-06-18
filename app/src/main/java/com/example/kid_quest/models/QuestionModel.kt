package com.example.kid_quest.models

data class QuestionModel(
    val question: String = "",
    val options: List<String> = emptyList(),
    val correctAnswerIndex: Int = -1,
    val imageUrl: String? = null
)
