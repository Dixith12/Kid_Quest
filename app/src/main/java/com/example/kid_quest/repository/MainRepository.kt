package com.example.kid_quest.repository

import android.util.Log
import com.example.kid_quest.data.DataorException
import com.example.kid_quest.data.Post
import com.example.kid_quest.data.User
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await

class MainRepository @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val postQuery: Query

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
            .addOnSuccessListener { result ->
                val userid = result.user?.uid
                if (userid != null) {
                    val user = User(
                        name = name,
                        dob = dob,
                        email = email,
                        uid = userid,
                        profilePic = null.toString()
                    )
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
            .addOnFailureListener {
                Log.d("ERROROR", "signIn: ${it.localizedMessage}")
            }
    }

    fun logOut() {
        auth.signOut()
    }

    suspend fun getAllPost(): DataorException<List<Post>, Boolean, Exception> {
        var dataorException = DataorException<List<Post>, Boolean, Exception>()
        try {
            dataorException.loading = true
            dataorException.data = postQuery.get().await().documents.map { documentSnapshot ->
                documentSnapshot.toObject(Post::class.java)!!
            }
            if (!dataorException.data.isNullOrEmpty()) dataorException.loading = false
        } catch (exeception: FirebaseException) {
            dataorException.exception = exeception
        }
        return dataorException
    }

}
