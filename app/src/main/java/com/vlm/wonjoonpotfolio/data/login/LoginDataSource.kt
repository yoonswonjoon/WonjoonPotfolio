package com.vlm.wonjoonpotfolio.data.login

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject


class LoginDataSource @Inject constructor(private val auth : FirebaseAuth){
    fun checkLogin(): FirebaseUser? {
       return auth.currentUser
    }

    fun checkSignIn(email : String, password : String): Task<AuthResult> {
        return auth.signInWithEmailAndPassword(email,password)
    }

}