package com.vlm.wonjoonpotfolio.data.useCase

import com.vlm.wonjoonpotfolio.data.ImgData.ImgDataRepository
import com.vlm.wonjoonpotfolio.data.user.User
import com.vlm.wonjoonpotfolio.data.user.UserForUi
import com.vlm.wonjoonpotfolio.data.user.UserRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetUserUseCase
@Inject
constructor(
    private val userRepository: UserRepository,
    private val imgDataRepository: ImgDataRepository
) {
    operator fun invoke(list : List<String>) = flow {
        try {
            val user = userRepository.getUser(list)//.map { it.uri }

            emit(user.map {
                it.toUserForUi(imgDataRepository.getImageUpgrade(it.uri))
            })
        }catch (e:Exception){
            val a = 0
        }
    }
}