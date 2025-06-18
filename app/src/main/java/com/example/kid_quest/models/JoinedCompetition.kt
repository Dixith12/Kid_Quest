package com.example.kid_quest.models

data class JoinedCompetition(val competitionName: String = "",
                             val userAnswers: List<Int> = emptyList(),
                             val score: Int = 0,
                             val totalQuestions: Int = 0,
                             val timestamp: Long = System.currentTimeMillis())
