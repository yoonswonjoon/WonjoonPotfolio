package com.vlm.wonjoonpotfolio.data.useCase

import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.vlm.wonjoonpotfolio.data.ImgData.ImgDataRepository
import com.vlm.wonjoonpotfolio.data.user.UserForUi
import com.vlm.wonjoonpotfolio.data.user.UserRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetUserUseCase
@Inject
constructor(
    private val userRepository: UserRepository,
    private val imgDataRepository: ImgDataRepository,
    private val firebaseCrashlytics: FirebaseCrashlytics
) {

    companion object {
        const val TAG = "GetUserUseCase"
    }
    operator fun invoke(list : List<String>) = flow {
        val rt = mutableListOf<UserForUi>()

        try {
            val user = userRepository.getUser(list)//.map { it.uri }
            emit(
                user.map { it.toUserForUi(null) }
            )

            emit(user.map {
                it.toUserForUi(imgDataRepository.getImageUpgrade(it.uri))
            })
        }catch (e:Exception){

            firebaseCrashlytics.log("$TAG : ${e.message?: "unknown"}")
//            emit(listOf())
        }
    }
}