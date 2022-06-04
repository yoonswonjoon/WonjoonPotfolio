package com.vlm.wonjoonpotfolio.data.login

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.vlm.wonjoonpotfolio.domain.PreferencesKey
import com.vlm.wonjoonpotfolio.domain.ResultState
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val loginDataSource: LoginDataSource,
    private val loginDataStorage : DataStore<Preferences>
    ) {

    fun autoLogin() = flow {
        try {
            emit(ResultState.loading())
            val id = loginDataStorage.data.first()[PreferencesKey.LOGIN_ID]
            val password = loginDataStorage.data.first()[PreferencesKey.LOGIN_PASSWORD]
            if(id!=null && password !=null){
                var email : String? = null
                loginDataSource.firebaseLogin(id,password).addOnSuccessListener {
                    email = it.user?.email
                }.addOnFailureListener {
                    val a = 0
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
            }.addOnFailureListener {
                val a = 0
            }.await()

            if(email == null){
                emit(ResultState.error("login failed"))
            }else{
                emit(ResultState.success(email))
            }
        }catch (e:Exception){
            emit(ResultState.error("login failed"))
        }
    }

    fun firebaseSignIn(/*id: String, password: String*/) = flow {
        try {
//            loginDataStorage.edit { preferences ->
//                preferences[PreferencesKey.LOGIN_ID] = id
//                preferences[PreferencesKey.LOGIN_PASSWORD] = password
//            }
            val id = loginDataStorage.data.first()[PreferencesKey.LOGIN_ID]!!
            val password = loginDataStorage.data.first()[PreferencesKey.LOGIN_PASSWORD]!!
            var email : String? = null
            loginDataSource.firebaseSignIn(id, password).addOnSuccessListener {
                loginDataSource.firebaseLogin(id,password).addOnSuccessListener {
                    email = it.user?.email
                }.addOnFailureListener {

                }
            }.addOnFailureListener {

            }.await()
            if(email == null){
                emit(ResultState.error("login failed"))
            }else{
                emit(ResultState.success(email))
            }
        }catch (e:Exception){
            emit(ResultState.error("login failed"))
        }
    }

    fun checkLogin() = loginDataSource.checkLogin()

}