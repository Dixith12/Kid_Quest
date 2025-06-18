package com.example.kid_quest.screens.competition

import com.example.kid_quest.models.CompetitionModel

data class UiState(
    var isLoading: Boolean = false,
    val fetchbyId: CompetitionModel?=null,
    val allcompetition: List<CompetitionModel> = emptyList(),
    val errorMessage: String? = null
)
