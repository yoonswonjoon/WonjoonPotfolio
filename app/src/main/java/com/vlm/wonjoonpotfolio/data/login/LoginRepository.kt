package com.vlm.wonjoonpotfolio.data.login

import javax.inject.Inject

class LoginRepository @Inject constructor(private val loginDataSource: LoginDataSource) {
    suspend fun checkLogin() = loginDataSource.checkLogin()
}