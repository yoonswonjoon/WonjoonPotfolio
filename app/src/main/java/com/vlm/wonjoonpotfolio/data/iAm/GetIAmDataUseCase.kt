package com.vlm.wonjoonpotfolio.data.iAm

import javax.inject.Inject

class GetIAmDataUseCase @Inject constructor(private val iAmRepository: IAmRepository) {
    fun act() = iAmRepository.test()
//    operator fun invoke() = /*iAmRepository.getMainView*/()
}