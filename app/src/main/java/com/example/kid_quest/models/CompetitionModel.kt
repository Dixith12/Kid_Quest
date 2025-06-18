package com.example.kid_quest.models

data class CompetitionModel(
    val id: String = "",
    val quizName: String = "",
    val description: String = "",
    val createdBy: String = "",
    val questions: List<QuestionModel> = emptyList(),
    val timestamp: Long = System.currentTimeMillis(),
    val status: String = "pending"
)

