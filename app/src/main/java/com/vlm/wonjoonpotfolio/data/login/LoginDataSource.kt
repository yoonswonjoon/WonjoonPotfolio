package com.vlm.wonjoonpotfolio.data.login

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.vlm.wonjoonpotfolio.data.user.User
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class LoginDataSource @Inject constructor(
    private val auth : FirebaseAuth,
    private val firebase : FirebaseFirestore
    ){
    fun checkLogin(): FirebaseUser? {
       return auth.currentUser
    }

    fun firebaseLogin(email: String, password: String): Task<AuthResult> {
        return auth.signInWithEmailAndPassword(email,password)
    }

    fun firebaseSignIn(email : String, password : String,): Task<AuthResult> {
        return auth.createUserWithEmailAndPassword(email,password)
    }


    suspend fun checkUserDataExist() =
        firebase.collection("app_user").document(auth.currentUser?.uid?: "").get().await().toObject(User::class.java)

    fun setUserData(user : User) = firebase.collection("app_user").document(auth.currentUser?.uid?: "").set(user)

    fun logOut() = auth.signOut()
}