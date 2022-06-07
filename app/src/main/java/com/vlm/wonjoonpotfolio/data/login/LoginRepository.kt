package com.vlm.wonjoonpotfolio.data.login

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.vlm.wonjoonpotfolio.domain.PreferencesKey
import com.vlm.wonjoonpotfolio.domain.ResultState
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val loginDataSource: LoginDataSource,
    private val loginDataStorage : DataStore<Preferences>,
    private val firebaseCrashlytics: FirebaseCrashlytics
    ) {

    companion object {
        const val TAG = "LoginRepository"
    }

    fun autoLogin() = flow {
        try {
            emit(ResultState.loading())
            val id = loginDataStorage.data.first()[PreferencesKey.LOGIN_ID]
            val password = loginDataStorage.data.first()[PreferencesKey.LOGIN_PASSWORD]

            if(id!=null && password !=null){
                var email : String? = null

                loginDataSource.firebaseLogin(id,password).addOnSuccessListener {
                    email = it.user?.email
                    firebaseCrashlytics.setUserId(email?: "unKnown")
                }.addOnFailureListener {
                    firebaseCrashlytics.log("$TAG : ${it.message?: "unknown"}")
                }.await()

                if(email == null){
                    emit(ResultState.error("login failed"))
                }else{
                    emit(ResultState.success(email))
                }
            }else{
                emit(ResultState.error("login failed"))
            }
        }catch (e:Exception){
            firebaseCrashlytics.log("$TAG : ${e.message?: "unknown"}")
            emit(ResultState.error("login failed"))
        }
    }

    fun firebaseLogin(id: String, password: String) = flow {
        try {
            emit(ResultState.loading())
            loginDataStorage.edit { preferences ->
                preferences[PreferencesKey.LOGIN_ID] = id
                preferences[PreferencesKey.LOGIN_PASSWORD] = password
            }
            var email : String? = null
            loginDataSource.firebaseLogin(id,password).addOnSuccessListener {
                email = it.user?.email
                firebaseCrashlytics.setUserId(email?: "unKnown")
            }.addOnFailureListener {
                firebaseCrashlytics.log("$TAG : ${it.message?: "unknown"}")
            }.await()

            if(email == null){
                emit(ResultState.error("login failed"))
            }else{
                emit(ResultState.success(email))
            }
        }catch (e:Exception){
            firebaseCrashlytics.log("$TAG : ${e.message?: "unknown"}")
            emit(ResultState.error("login failed"))
        }
    }

    fun firebaseSignIn() = flow {
        try {
            val id = loginDataStorage.data.first()[PreferencesKey.LOGIN_ID]!!
            val password = loginDataStorage.data.first()[PreferencesKey.LOGIN_PASSWORD]!!
            var email : String? = null
            var success  = false
            loginDataSource.firebaseSignIn(id, password).addOnSuccessListener {
                success = true
            }.addOnFailureListener {

            }.await()

            if(success){
                loginDataSource.firebaseLogin(id,password).addOnSuccessListener {
                    email = it.user?.email
                    firebaseCrashlytics.setUserId(email?: "unKnown")
                }.addOnFailureListener {
                    firebaseCrashlytics.log("$TAG : ${it.message?: "unknown"}")
                }.await()
            }
            if(email == null){
                emit(ResultState.error("login failed"))
            }else{
                emit(ResultState.success(email))
            }
        }catch (e:Exception){
            firebaseCrashlytics.log("$TAG : ${e.message?: "unknown"}")
            emit(ResultState.error("login failed"))
        }
    }

    fun checkLogin() = loginDataSource.checkLogin()

}