package com.vlm.wonjoonpotfolio.data.useCase

import com.vlm.wonjoonpotfolio.data.login.LoginRepository
import javax.inject.Inject

class LoginCheckUserCase
@Inject
constructor(
    private val loginRepository: LoginRepository
) {
    operator fun invoke() =
        loginRepository.checkLogin()
}