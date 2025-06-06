package com.example.kid_quest.repository

import android.R.attr.id
import android.net.Uri
import com.example.kid_quest.data.Post
import android.util.Log
import com.example.kid_quest.R
import com.example.kid_quest.data.DataorException

import com.example.kid_quest.data.User
import com.example.kid_quest.screens.homeScreen.UiState
import com.example.kid_quest.utils.DateTimeFormat
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.storage.FirebaseStorage
import jakarta.inject.Inject
import kotlinx.coroutines.tasks.await
import kotlin.collections.isNullOrEmpty

class MainRepository @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val postQuery: Query
) {
    val postCollection = firestore.collection("posts")
    val storageRef = FirebaseStorage.getInstance().reference
    val userCollection=firestore.collection("users")

    val auth = FirebaseAuth.getInstance()

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
                val defaultUrl = "https://firebasestorage.googleapis.com/v0/b/kinder-jogi.firebasestorage.app/o/Profile_Pic.jpg?alt=media&token=0d84988d-c190-4eca-81aa-4350fcc03aad"
                if (userid != null) {
                    val user = User(
                        name = name,
                        profilePic = defaultUrl,
                        dob = dob,
                        email = email,
                        uid = userid
                    )
                    firestore.collection("users").document(userid)
                        .set(user)
                        .addOnSuccessListener {
                            onSuccess()
                        }
                        .addOnFailureListener {
                            onFailure(it)
                        }
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

    fun forgotPassword(email: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        auth.sendPasswordResetEmail(email)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener {
                onFailure(it)
            }
    }

    suspend fun getAllPost(): DataorException<List<Post>, Boolean, Exception> {
        val dataorException = DataorException<List<Post>, Boolean, Exception>()
        try {
            dataorException.loading = true
            dataorException.data = postQuery.get().await().documents.map { doc ->
                val post = doc.toObject(Post::class.java)
                post?.copy(postId = doc.id)!!
            }
            if (!dataorException.data.isNullOrEmpty()) dataorException.loading = false
        } catch (exeception: FirebaseException) {
            dataorException.exception = exeception
        }
        return dataorException
    }

    suspend fun SavePostFirestore(
        imageuris: List<Uri>,
        username: String,
        userprofile: String,
        description: String,
        userid: String
    ): Result<Unit> {
        return try {
            val postid = postCollection.document().id
            val uploadImageurls = mutableListOf<String>()

            imageuris.forEachIndexed { index, uri ->
                val imageref = storageRef.child("posts/$postid/Post_${index + 1}.jpg")
                val uploadTask = imageref.putFile(uri).await()
                val downloadUrl = uploadTask.storage.downloadUrl.await()
                uploadImageurls.add(downloadUrl.toString())
            }

            val post = Post(
                postId = postid,
                userId = userid,
                description = description,
                username = username,
                userProfileUrl = userprofile,
                timestamp = DateTimeFormat(),
                imageUrls = uploadImageurls,
                likes = 0,
                likedBy = emptyList()
            )
            postCollection.document(postid).set(post).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


    fun updateProfile(name: String,
                      userid: String,
                      dob: String,
                      profileimage: Uri?,
                      onSuccess: () -> Unit,
                      onFailure: (Exception) -> Unit)
    {

        if(profileimage!=null)
        {
            val imageref = storageRef.child("users/$userid/profile.jpg")
            imageref.putFile(profileimage)
                .addOnSuccessListener {
                    imageref.downloadUrl.addOnSuccessListener {
                        val updateData = mapOf(
                            "name" to name,
                            "dob" to dob,
                            "profilePic" to it.toString()
                        )

                            userCollection.document(userid).update(updateData)
                            .addOnSuccessListener {
                                onSuccess()
                            }
                            .addOnFailureListener {
                                onFailure(it)
                            }
                    }
                        .addOnFailureListener {
                            onFailure(it)
                        }
                }
                .addOnFailureListener {
                    onFailure(it)
                }

            }


        else
        {
            val updatedData = mapOf(
                "name" to name,
                "dob" to dob
            )
            userCollection.document(userid).update(updatedData)
                .addOnSuccessListener { onSuccess() }
                .addOnFailureListener { onFailure(it) }
        }
    }
}
