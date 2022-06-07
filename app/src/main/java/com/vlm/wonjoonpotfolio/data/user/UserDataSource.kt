package com.vlm.wonjoonpotfolio.data.user

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserDataSource @Inject constructor(private val firebaseFirestore: FirebaseFirestore){

    // 사용자 아닌 내가 지정한 사람 (나 , 형)
    suspend fun getUser(list : List<String>) : List<User> {
        val doc = firebaseFirestore.collection("user").whereIn("nickname",list).get().await().map {
            it.toObject(User::class.java)!!
        }
        return doc
    }

    // 회원가입 한 사용자
//    suspend fun getAppUserData(uid : String) : MutableMap<String, Any>? {
//        var user : User? = null
//        return firebaseFirestore.collection("app_user").document(uid).get().addOnSuccessListener {
//
//        }.addOnFailureListener {
//
//        }.await().toObject(User::class.java)
//    }


}