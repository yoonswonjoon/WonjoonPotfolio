package com.vlm.wonjoonpotfolio.data.useCase

import com.vlm.wonjoonpotfolio.data.login.LoginRepository
import javax.inject.Inject

class GetCurrentUser @Inject constructor(
    private val loginDataRepository: LoginRepository
) {
    suspend operator fun invoke() {
        loginDataRepository.currentUserData()
    }
}