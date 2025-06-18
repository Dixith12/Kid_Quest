package com.example.kid_quest.screens.competition

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kid_quest.models.QuestionModel
import com.example.kid_quest.repository.MainRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompetitionViewModel @Inject constructor(private val repository: MainRepository):ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState

    val auth = FirebaseAuth.getInstance().currentUser
    fun submitCompetitionToFirestore(
        quizName: String,
        description: String,
        questions: List<QuestionModel>,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            repository.submitCompetition(quizName, description, uid, questions,onSuccess,onFailure)
        }
    }

    fun fetchByIdApprove(id:String)
    {
        viewModelScope.launch {
            try{
                _uiState.value = _uiState.value.copy(isLoading = true)
                val fetch = repository.fetchByIdApprove(id)
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    fetchbyId = fetch)
            }
            catch (e:Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = e.message
                )
            }
        }
    }

    fun joinQuizSubmit(
                       competitionId: String,
                       competitionName:String,
                       userAnswer:List<Int>,
                       correctAnswer: List<Int>,
                       totalQuestion:Int,
                       onSuccess: () -> Unit,
                       onFailure: (Exception) -> Unit
    )
    {
        val userId = auth?.uid
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            if (userId != null) {
                repository.joinQuizSubmit(userId = userId,
                    competitionId = competitionId,
                    competitionName = competitionName,
                    userAnswer = userAnswer,
                    correctAnswer = correctAnswer,
                    totalQuestion = totalQuestion,
                    onSuccess = {
                        onSuccess()
                    },
                    onFailure = {
                        onFailure(it)
                    }
                )
            }
        }
    }

    fun getAllCompetition()
    {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            val response = repository.getAllCompetition()
            _uiState.value = _uiState.value.copy(isLoading = true,
                allcompetition = response)
        }
    }
}