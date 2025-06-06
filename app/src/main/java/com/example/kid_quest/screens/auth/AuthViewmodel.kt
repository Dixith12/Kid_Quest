package com.example.kid_quest.screens.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kid_quest.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewmodel @Inject constructor(private val repository: MainRepository) : ViewModel() {
    fun createAccount(
        email: String,
        password: String,
        name: String,
        dob: String,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) = viewModelScope.launch{
        repository.createAccount(email, password, name, dob, onSuccess, onFailure)
    }

    fun signIn(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) = viewModelScope.launch(Dispatchers.IO) {
        repository.signIn(email, password, onSuccess= onSuccess, onFailure)
    }

    fun forgotPassword(email: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit)
    {
        repository.forgotPassword(email, onSuccess, onFailure)

    }
}