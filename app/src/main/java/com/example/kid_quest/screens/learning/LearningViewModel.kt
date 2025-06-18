package com.example.kid_quest.screens.learning

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kid_quest.repository.MainRepository
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LearningViewModel @Inject constructor( private val repository: MainRepository) : ViewModel() {

    val user = Firebase.auth.currentUser
    private val _videoUiState = MutableStateFlow(VideoUiState())
    val videoUiState: StateFlow<VideoUiState> = _videoUiState

    fun fetchVideos(classLevel: Int) {
        viewModelScope.launch {
            Log.d("VideoDebug", "Fetching videos for class $classLevel")
            _videoUiState.value = _videoUiState.value.copy(isLoading = true, error = null)

            try {
                repository.getVideosForClass(classLevel).collect { videoList ->
                    Log.d("VideoDebug", "Fetched ${videoList.size} videos")
                    videoList.forEach { video ->
                        Log.d("VideoDebug", "Video: ${video.title}, ID: ${video.videoId}")
                    }
                    _videoUiState.value = _videoUiState.value.copy(
                        videos = videoList,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                Log.e("VideoDebug", "Error fetching videos: ${e.message}", e)
                _videoUiState.value = _videoUiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Something went wrong"
                )
            }
        }
    }
}
