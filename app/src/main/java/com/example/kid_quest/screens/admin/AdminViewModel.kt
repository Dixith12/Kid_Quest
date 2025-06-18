package com.example.kid_quest.screens.admin

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kid_quest.models.CompetitionModel
import com.example.kid_quest.models.User
import com.example.kid_quest.repository.MainRepository
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdminViewModel @Inject constructor(private  val repository: MainRepository): ViewModel(){
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState

    val user = FirebaseAuth.getInstance().currentUser

    init {
        getPending()

        FirebaseAuth.getInstance().currentUser?.let {
            fetchUserData()
        }
    }

    fun logOut() {
        repository.logOut()
    }
    fun fetchUserData() {
       user?.uid?.let { uid ->
            Firebase.firestore.collection("users").document(uid).get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        val user = document.toObject(User::class.java)
                        _uiState.update { it.copy(user = user) }
                    }
                }
                .addOnFailureListener {
                    _uiState.update { it.copy(errorMessage = it.errorMessage) }
                }
        }
    }

    fun addVideo(videoTitle: String, videoId: String, classLevel: Int, onSuccess:()->Unit,onFailure:(Exception)->Unit) {
        viewModelScope.launch {
            try {
                repository.addVideoToFirestore(
                    videoTitle, videoId, classLevel,
                    onSuccess = onSuccess,
                    onFailure = onFailure
                )
            } catch (e: Exception) {
                Log.e("ViewModel", "Failed to add video: ${e.message}")
            }
        }
    }
    private fun getPending()
    {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try{
                val result = repository.getPending()
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    pendingCompetitions = result
                )
            }
            catch (e:Exception){
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = e.message
                )
            }

        }
    }
    fun fetchById(id:String)
    {
        viewModelScope.launch {
            try{
            _uiState.value = _uiState.value.copy(isLoading = true)
            val fetch = repository.fetchById(id)
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
    fun onApprove(competitionModel: CompetitionModel,
                  onSuccess: () -> Unit,
                  onFailure: (Exception) -> Unit)
    {
        viewModelScope.launch {
            repository.onApprove(competitionModel,onSuccess,onFailure)
        }
    }

    fun onDecline(competitionId:String,
                  onSuccess: () -> Unit,
                  onFailure: (Exception) -> Unit){
        viewModelScope.launch {
            repository.onDecline(
                competitionId,
                onSuccess = {
                    onSuccess()
                },
                onFailure = {
                    onFailure(it)
                })
        }
    }

}