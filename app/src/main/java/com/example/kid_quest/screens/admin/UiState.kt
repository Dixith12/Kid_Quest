package com.example.kid_quest.screens.admin

import com.example.kid_quest.models.CompetitionModel
import com.example.kid_quest.models.User

data class UiState(
    var user: User? = null,
    val isLoading: Boolean = false,
    val pendingCompetitions: List<CompetitionModel> = emptyList(),
    val fetchbyId: CompetitionModel? = null,
    val errorMessage: String? = null
)