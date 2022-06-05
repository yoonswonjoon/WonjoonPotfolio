package com.vlm.wonjoonpotfolio.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestoreSettings
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import javax.inject.Singleton

@InstallIn(ViewModelComponent::class)
@Module
object FirebaseModule{
//    @JvmStatic
//    @Singleton
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
//    @JvmStatic
//    @Singleton
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
//    @JvmStatic
//    @Singleton
    @Provides
    fun provideFireAuth() : FirebaseAuth{
        return FirebaseAuth.getInstance()
    }
//    @JvmStatic
//    @Singleton
    @Provides
    fun providesFireCrashlytics() : FirebaseCrashlytics = FirebaseCrashlytics.getInstance()
}