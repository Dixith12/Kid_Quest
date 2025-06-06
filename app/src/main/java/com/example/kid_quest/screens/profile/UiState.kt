package com.example.kid_quest.screens.profile

import com.example.kid_quest.models.CompetitionModel
import com.example.kid_quest.models.JoinedCompetition
import com.example.kid_quest.models.User

data class UiState(
    var user: User? = null,
    val isUploading: Boolean = false,
    val getStatus:List<CompetitionModel> = emptyList(),
    val joinedCompetition: List<JoinedCompetition> = emptyList()
)
