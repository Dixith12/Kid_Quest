package com.example.kid_quest.screens.homeScreen

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kid_quest.repository.MainRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: MainRepository) : ViewModel() {

    var uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState())
        private set

    val firestore = FirebaseFirestore.getInstance()

    init {
        getPost()
    }

    fun getPost() {
        viewModelScope.launch {
            val currentUser = FirebaseAuth.getInstance().currentUser?.uid
            val result = repository.getAllPost()
            val updatedPosts = result.data?.map { post ->
                val isLikedByCurrentUser = post.likedBy.contains(currentUser)
                post.copy(isLikedByCurrentUser = isLikedByCurrentUser)
            }
            uiState.update {
                it.copy(
                    posts = result.copy(data = updatedPosts)
                )
            }
        }
    }

    fun likePost(postId: String) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val postRef = firestore.collection("posts").document(postId)

        viewModelScope.launch {
            try {
                firestore.runTransaction { transaction ->
                    val snapshot = transaction.get(postRef)
                    val currentLikes = snapshot.getLong("likes") ?: 0
                    val likedBy = snapshot.get("likedBy") as? List<String> ?: emptyList()

                    val hasLiked = userId in likedBy
                    val newLikes = if (hasLiked) currentLikes - 1 else currentLikes + 1
                    val updatedLikedBy = if (hasLiked)
                        likedBy - userId
                    else
                        likedBy + userId

                    transaction.update(postRef, mapOf(
                        "likes" to newLikes,
                        "likedBy" to updatedLikedBy
                    ))
                }.await()

                // Update UI
                uiState.update { state ->
                    state.copy(
                        posts = state.posts?.copy(
                            data = state.posts!!.data?.map { post ->
                                if (post.postId == postId) post.copy(
                                    likes = if (post.isLikedByCurrentUser) post.likes - 1 else post.likes + 1,
                                    isLikedByCurrentUser = !post.isLikedByCurrentUser
                                ) else post
                            }
                        )
                    )
                }

            } catch (e: Exception) {
                Log.e("likePost", "Transaction failed: ${e.localizedMessage}")
            }
        }
    }



    suspend fun SavePostFirestore(
        imageuris: List<Uri>,
        description: String,
    ) {
        uiState.value = uiState.value.copy(isUploading = true)
        val auth = FirebaseAuth.getInstance()
        val userId = auth.currentUser?.uid
        val userDoc = userId?.let { firestore.collection("users").document(it).get().await() }
        val username = userDoc?.getString("name") ?: "Anonymous"
        val profileUrl = userDoc?.getString("profilePic") ?: ""
        viewModelScope.launch {
            try {
                if (userId != null) {
                    repository.SavePostFirestore(
                        imageuris,
                        username,
                        profileUrl,
                        description,
                        userId
                    )
                }
                uiState.value = uiState.value.copy(
                    uploadState = Result.success(Unit),
                    isUploading = false
                )
            } catch (e: Exception) {
                uiState.value = uiState.value.copy(
                    uploadState = Result.failure(e),
                    isUploading = false
                )
            }
        }
    }

    fun clearUploadState() {
        uiState.value = uiState.value.copy(uploadState = null, isUploading = false)


    }
}