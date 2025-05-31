package com.example.kid_quest.repository

import android.net.Uri
import com.example.kid_quest.data.Post
import android.util.Log
import com.example.kid_quest.data.DataorException

import com.example.kid_quest.data.User
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
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val postQuery: Query
) {
    val postCollection = firestore.collection("posts")
    val storageRef = FirebaseStorage.getInstance().reference

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


    suspend fun SavePostFirestore(
        imageuris: List<Uri>,
        username:String,
        userprofile:String,
        description:String,
        userid:String
    ){
        try{
            val postid = postCollection.document().id
            val uploadImageurls = mutableListOf<String>()

            imageuris.forEachIndexed { index, uri->
                val imageref = storageRef.child("post/$postid/Post_${index+1}.jpg")
                val uploadTask = imageref.putFile(uri).await()
                val downloadUrl = uploadTask?.storage?.downloadUrl?.await()
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
                likes = 0
            )
            postCollection.document(postid).set(post).await()
            Result.success(Unit)
        }
        catch (e:Exception)
        {
            Result.failure<Exception>(e)
        }
    }

}
