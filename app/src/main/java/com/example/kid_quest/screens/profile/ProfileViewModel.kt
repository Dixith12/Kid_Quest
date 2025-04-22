package com.example.kid_quest.screens.profile

import androidx.lifecycle.ViewModel
import com.example.kid_quest.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel @Inject constructor(private val repository: MainRepository): ViewModel() {
    fun logOut()
    {
        repository.logOut()
    }
}