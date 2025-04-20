package com.example.kid_quest.repository

import android.provider.ContactsContract.CommonDataKinds.Email
import android.util.Log
import com.example.kid_quest.data.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import jakarta.inject.Inject

class MainRepository @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) {

    fun createAccount(
        email: String,
        password: String,
        name: String,
        dob: String,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener { result->
                val userid = result.user?.uid
                if (userid != null) {
                    val user = User(name=name, dob=dob, email = email, uid = userid, profilePic = null.toString())
                    firestore.collection("users").document(userid)
                        .set(user)
                    onSuccess()
                }

            }
            .addOnFailureListener {
                onFailure(it)
            }

    }

    fun signIn(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener{
                Log.d("ERROROR", "signIn: ${it.localizedMessage}")
            }
    }

}
