package com.vlm.wonjoonpotfolio.data.login

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.internal.api.FirebaseNoSignedInUserException
import com.google.firebase.storage.StorageException
import com.vlm.wonjoonpotfolio.data.user.User
import com.vlm.wonjoonpotfolio.domain.*
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

    fun checkLogin() = loginDataSource.checkLogin()


    fun logOut() = loginDataSource.logOut()


////////////////////////////////////////////////////////////////////////////////////////////////////

    suspend fun logOutV2() {
        loginDataSource.logOut()
        clearLoginData()
        // AppUser 클리어
    }

    suspend fun clearLoginData(){
        loginDataStorage.edit { preferences->
            preferences.remove(PreferencesKey.LOGIN_ID)
            preferences.remove(PreferencesKey.LOGIN_PASSWORD)
        }
    }

    suspend fun getLoginData(): Pair<String?, String?> {
        val id = loginDataStorage.data.first()[PreferencesKey.LOGIN_ID]
        val password = loginDataStorage.data.first()[PreferencesKey.LOGIN_PASSWORD]

        return id to password
    }

    suspend fun setLoginData(id:String, password: String) {
        loginDataStorage.edit { preferences->
            preferences[PreferencesKey.LOGIN_ID] = id
            preferences[PreferencesKey.LOGIN_PASSWORD] = password
        }
    }

    suspend fun loginV2(id: String, password: String) : LoginResult {
        try {
            val user = loginDataSource.firebaseLogin(id,password)
                .await().user
            user?.let { firebaseUser->
                setLoginData(id,password)
                val firebaseUserData = loginDataSource.getUserData()
                firebaseUserData?.let{ userData ->
                    return LoginResult(data = userData)
                }?: kotlin.run {
                    val newUserData = User(
                        eid = firebaseUser.email!!,
                        uid = firebaseUser.uid
                    )
                    var success = false
                    loginDataSource.setUserData(newUserData).addOnSuccessListener{
                        success = true
                    }.await()
                    if(success) {
                        return LoginResult(data = newUserData)
                    }else{
                        logOutV2()
                        return LoginResult(error = LoginErrorType.SettingBasicDataFailed)
                    }
                }
            }?: return LoginResult(error = LoginErrorType.NotSignedIn)
        }catch (e:Exception){
            return LoginResult(error = LoginErrorType.NotSignedIn)
//            return LoginResult(error = LoginErrorType.OtherError) 에러 세분화 과정 필요
        }
    }

    suspend fun signIn(id: String, password: String): Boolean {
        var success = false
        loginDataSource.firebaseSignIn(id,password).addOnSuccessListener {
            success= true
        }.await()
        return success
    }

    suspend fun autoLoginV2(): User? {
        try {
            val loginData = getLoginData()
            if(loginData.first != null && loginData.second != null){
                val login = loginV2(loginData.first!!,loginData.second!!)
                if(login.data != null){
                    return login.data
                }else{
                    return null
                }
            }
            return null
        }catch (e:Exception){
            return null
        }
    }

}
