package com.vlm.wonjoonpotfolio.data.user

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserDataSource @Inject constructor(private val firebaseFirestore: FirebaseFirestore){
    suspend fun getUser(list : List<String>) : List<User> {


        val doc = firebaseFirestore.collection("user").whereIn("nickname",list).get().await().map {
            it.toObject(User::class.java)!!
        }
        return doc
    }
}