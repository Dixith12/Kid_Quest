package com.example.kid_quest.screens.profile

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kid_quest.models.User
import com.example.kid_quest.repository.MainRepository
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel @Inject constructor(private val repository: MainRepository) : ViewModel() {

    fun logOut() {
        repository.logOut()
    }

    private val currentUser = FirebaseAuth.getInstance()

    var uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState())
        private set


    init {
        fetchUserData()
    }

    fun fetchUserData() {
        currentUser.uid?.let { uid ->
            Firebase.firestore.collection("users").document(uid).get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        val user = document.toObject(User::class.java)
                        uiState.update { it.copy(user = user) }
                    }
                }
        }
    }

    fun updateProfile(
        name: String,
        dob: String,
        profileimage: Uri?,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        try {
            uiState.update {
                it.copy(isUploading = true)
            }
            currentUser.uid?.let { uid->
                repository.updateProfile(
                    name = name,
                    userid = uid,
                    dob = dob,
                    profileimage = profileimage,
                    onSuccess = {
                        uiState.update { it.copy(isUploading = false) }
                        onSuccess()
                    },
                    onFailure = { e ->
                        uiState.update { it.copy(isUploading = false) }
                        onFailure(e)
                    }
                )
            }
        }
        catch (e:Exception)
        {
            uiState.update {
                it.copy(isUploading = false)
            }

        }
    }

    fun getStatus()
    {
        val currentuser = FirebaseAuth.getInstance().currentUser
        viewModelScope.launch {
            try {

                val uid= currentuser?.uid
                uiState.update {
                    it.copy(isUploading = true)
                }
                if(uid!=null) {
                    val result = repository.getStatus(uid)
                    uiState.update {
                        it.copy(
                            isUploading = false,
                            getStatus = result
                        )
                    }
                }else {
                    uiState.update { it.copy(isUploading = false) }
                }

            }
            catch (e:Exception)
            {
                Log.d("ee","Error}")
                uiState.update { it.copy(isUploading = false) }
            }
        }
    }

    fun joinedCompetition()
    {
        val currentuser = FirebaseAuth.getInstance().currentUser
        viewModelScope.launch {
            try {
                val uid= currentuser?.uid
                uiState.update {
                    it.copy(isUploading = true)
                }
                if(uid!=null) {
                    val result = repository.joinedCompetition(uid)
                    uiState.update {
                        it.copy(
                            isUploading = false,
                            joinedCompetition = result
                        )
                    }
                }else {
                    uiState.update { it.copy(isUploading = false) }
                }

            }
            catch (e:Exception)
            {
                uiState.update { it.copy(isUploading = false) }
            }
        }
    }
}