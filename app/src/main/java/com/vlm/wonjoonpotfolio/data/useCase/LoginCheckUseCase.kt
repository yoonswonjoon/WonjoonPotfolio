package com.vlm.wonjoonpotfolio.data.useCase

import com.vlm.wonjoonpotfolio.data.login.LoginRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginCheckUseCase @Inject constructor(private val loginRepository: LoginRepository){
    operator fun invoke() = flow {
        emit(loginRepository.checkLogin())
    }
}