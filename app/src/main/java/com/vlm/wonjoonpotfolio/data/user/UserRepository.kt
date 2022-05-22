package com.vlm.wonjoonpotfolio.data.user

import javax.inject.Inject

class UserRepository @Inject constructor(private val userDataSource: UserDataSource) {
    suspend fun getUser(list : List<String>) = userDataSource.getUser(list)
}