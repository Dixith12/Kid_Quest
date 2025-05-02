package com.example.kid_quest.di

import com.example.kid_quest.repository.MainRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides

    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideFireStore(): FirebaseFirestore = FirebaseFirestore.getInstance()

    @Provides
    @Singleton
    @Named("postQuery")
    fun providePostQuery(): Query = FirebaseFirestore.getInstance().collection("posts").orderBy(
        "timestamp",
        Query.Direction.DESCENDING
    )

    @Provides
    @Singleton
    fun provideRepository(auth: FirebaseAuth, firestore: FirebaseFirestore): MainRepository =
        MainRepository(
            auth = auth,
            firestore = firestore,
            postQuery = FirebaseFirestore.getInstance().collection("posts").orderBy(
                "timestamp",
                Query.Direction.DESCENDING
            )
        )
}