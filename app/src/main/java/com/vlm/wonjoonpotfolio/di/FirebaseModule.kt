package com.vlm.wonjoonpotfolio.di

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestoreSettings
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module
object FirebaseModule{
    @Provides
    fun provideFirebaseFirestore() : FirebaseFirestore {
        val setting = firestoreSettings {
            isPersistenceEnabled = true
        }
        val firebaseFirestore =
            FirebaseFirestore.getInstance()
        firebaseFirestore.firestoreSettings = setting
        return firebaseFirestore
    }

    @Provides
    fun provideFireStorage() : FirebaseStorage{
//        val setting = firestoreSettings {
//            isPersistenceEnabled = true
//        }
//        val firebaseFirestore =
//            FirebaseFirestore.getInstance()
//        firebaseFirestore.firestoreSettings = setting

        return FirebaseStorage.getInstance()
    }
}