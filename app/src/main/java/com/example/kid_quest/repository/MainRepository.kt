package com.example.kid_quest.repository

import android.net.Uri
import com.example.kid_quest.models.Post
import android.util.Log
import com.example.kid_quest.models.CompetitionModel
import com.example.kid_quest.models.DataorException
import com.example.kid_quest.models.QuestionModel

import com.example.kid_quest.models.User
import com.example.kid_quest.models.VideoModel
import com.example.kid_quest.utils.DateTimeFormat
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.storage.FirebaseStorage
import jakarta.inject.Inject
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import kotlin.collections.isNullOrEmpty
import androidx.core.net.toUri
import com.example.kid_quest.models.JoinedCompetition

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
        onLoading: (Boolean) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        onLoading(true)
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

                onLoading(false)
            }
            .addOnFailureListener {
                onFailure(it)
                onLoading(false)
            }

    }

    fun signIn(
        email: String,
        password: String,
        onLoading: (Boolean) -> Unit,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        onLoading(true)
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                onSuccess()
                onLoading(false)
            }
            .addOnFailureListener {
                Log.d("ERROROR", "signIn: ${it.localizedMessage}")
                onFailure(it)
                onLoading(false)
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


    fun getVideosForClass(classLevel: Int): Flow<List<VideoModel>> = callbackFlow {
        val db = FirebaseFirestore.getInstance()

        val listener = db.collection("videos")
            .whereEqualTo("classLevel", classLevel)
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }

                val videos = snapshot?.documents?.mapNotNull { doc ->
                    val title = doc.getString("title") ?: ""
                    val rawVideoInput = doc.getString("videoId") ?: ""
                    val extractedVideoId = extractYouTubeVideoId(rawVideoInput)

                    val classLvl = doc.getLong("classLevel")?.toInt() ?: 0
                    val timestamp = doc.getTimestamp("timestamp")?.toDate()?.time ?: 0L

                    VideoModel(
                        title = title,
                        videoId = extractedVideoId,
                        classLevel = classLvl,
                        timestamp = timestamp
                    )
                } ?: emptyList()

                trySend(videos)
            }

        awaitClose { listener.remove() }
    }

    private fun extractYouTubeVideoId(url: String): String {
        val regex = Regex("""(?:https?://)?(?:www\.)?(?:youtube\.com/watch\?v=|youtu\.be/)([\w-]{11})""")
        val match = regex.find(url)
        return match?.groups?.get(1)?.value ?: url // fallback if ID not found
    }

    fun addVideoToFirestore(videoTitle: String, videoId: String, classLevel: Int,onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        val videoData = hashMapOf(
            "title" to videoTitle,
            "videoId" to videoId,
            "classLevel" to classLevel,
            "timestamp" to com.google.firebase.Timestamp.now()
        )

        FirebaseFirestore.getInstance()
            .collection("videos")
            .add(videoData)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { exe->
                onFailure(exe)
            }
    }

    suspend fun submitCompetition(
        quizName: String,
        description: String,
        createdBy: String,
        questions: List<QuestionModel>,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        try {
            val sanitizedQuizName = quizName.replace(" ", "_").lowercase()

            val questionsWithUrls = questions.mapIndexed { index, question ->
                if (!question.imageUrl.isNullOrEmpty() && question.imageUrl.startsWith("content://")) {
                    val imageUri = question.imageUrl.toUri()
                    val fileName = "${sanitizedQuizName}_${index + 1}.jpg"
                    val imageRef = storageRef.child("question_images/$sanitizedQuizName/$fileName")
                    imageRef.putFile(imageUri).await()
                    val downloadUrl = imageRef.downloadUrl.await().toString()
                    question.copy(imageUrl = downloadUrl)
                } else {
                    question
                }
            }

            val docRef = firestore.collection("pendings").document()
            val competition = CompetitionModel(
                id = docRef.id,
                quizName = quizName,
                description = description,
                createdBy = createdBy,
                questions = questionsWithUrls,
                timestamp = System.currentTimeMillis(),
                status = "pending"
            )

            docRef.set(competition).await()
            onSuccess()

        } catch (e: Exception) {
            Log.e("Repository", "Error submitting competition", e)
            onFailure(e)
        }
    }

    suspend fun getPending():List<CompetitionModel>
    {
        val pendings= firestore.collection("pendings")
        return try {
                val docs = pendings.get().await()
                docs.documents.mapNotNull { it.toObject(CompetitionModel::class.java) }
        }
        catch (e:Exception)
        {
            emptyList()
        }
    }

    suspend fun fetchById(competitionId: String): CompetitionModel? {
        return try {
            val doc = firestore.collection("pendings")
                .document(competitionId)
                .get()
                .await()

            if (doc.exists()) {
                doc.toObject(CompetitionModel::class.java)
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }


    fun onApprove(competiton: CompetitionModel,
                  onSuccess: () -> Unit,
                  onFailure: (Exception) -> Unit)
    {
        val pendings= firestore.collection("pendings").document(competiton.id)
        val approved = firestore.collection("competitions").document(competiton.id)

        val approvedCompetition = competiton.copy(status = "approved")
        approved.set(approvedCompetition)
            .addOnSuccessListener {
                pendings.delete()
                onSuccess()
            }
            .addOnFailureListener {e->
                onFailure(e)
            }
    }

    fun onDecline(
        competitonId: String,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit)
    {
        val pendings= firestore.collection("pendings").document(competitonId)
        pendings.delete().addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { e ->
                onFailure(e)
            }
    }

    fun joinQuizSubmit(userId:String,
                       competitionId: String,
                       competitionName:String,
                       userAnswer:List<Int>,
                       correctAnswer: List<Int>,
                       totalQuestion:Int,
                       onSuccess: () -> Unit,
                       onFailure: (Exception) -> Unit)
    {
        val joinedCompetition = FirebaseFirestore.getInstance().collection("joined").document(userId).collection("quiz").document(competitionId)
        val score = correctAnswer.zip(userAnswer).count { it.first == it.second }
        val join = JoinedCompetition(competitionName,correctAnswer,score,totalQuestion)

        joinedCompetition.set(join)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener {
                onFailure(it)
            }
    }

    suspend fun getAllCompetition():List<CompetitionModel>
    {
        return firestore.collection("competitions")
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .get()
            .await()
            .map { it.toObject(CompetitionModel::class.java) }
    }

    suspend fun fetchByIdApprove(competitionId: String): CompetitionModel? {
        return try {
            val doc = firestore.collection("competitions")
                .document(competitionId)
                .get()
                .await()

            if (doc.exists()) {
                doc.toObject(CompetitionModel::class.java)
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }

    suspend fun getStatus(userId:String):List<CompetitionModel>
    {
        val pendingsRef = firestore.collection("pendings")
            .whereEqualTo("createdBy",userId)
            .orderBy("timestamp",Query.Direction.DESCENDING)
        val approvedRef = firestore.collection("competitions")
            .whereEqualTo("createdBy",userId)
            .orderBy("timestamp",Query.Direction.DESCENDING)
        val pending = pendingsRef.get().await().toObjects(CompetitionModel::class.java)
        val approved = approvedRef.get().await().toObjects(CompetitionModel::class.java)

        val combined = (pending + approved).sortedByDescending { it.timestamp }
        return combined
    }

    suspend fun joinedCompetition(userId:String):List<JoinedCompetition>
    {
        val joinedRef = firestore.collection("joined")
            .document(userId)
            .collection("quiz")
            .orderBy("timestamp",Query.Direction.DESCENDING)
        val snapshot = joinedRef.get().await()
        if (!snapshot.isEmpty) {
            return snapshot.toObjects(JoinedCompetition::class.java)
        }
        return emptyList()
    }

}
