package com.vlm.wonjoonpotfolio.data.useCase

import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.vlm.wonjoonpotfolio.data.ImgData.ImgDataRepository
import com.vlm.wonjoonpotfolio.data.iAm.iAmTextData.IAmTextDataRepository
import com.vlm.wonjoonpotfolio.domain.ResultState
import com.vlm.wonjoonpotfolio.ui.iAm.IAmMainViewState
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import kotlin.Exception

class GetIAmDataUseCase
@Inject
constructor(
    private val iAmRepository: IAmTextDataRepository,
    private val imgDataRepository: ImgDataRepository,
    private val firebaseCrashlytics: FirebaseCrashlytics
) {

    companion object{
        const val TAG = "GetIAmDataUseCase"
    }
    operator fun invoke(path : String = ""): Flow<ResultState<IAmMainViewState>> = flow {
        try {
            emit(ResultState.loading())
            var data = iAmRepository.getMainView().toIAmViewState()
            emit(ResultState.success(data))
            val uri = imgDataRepository.getImageUpgrade(path)
            data = data.copy(img = uri, imgLoading = false)
            emit(ResultState.success(data))
        }catch (e:Exception){
            firebaseCrashlytics.log("TAG : ${e.message?:"I Don't know"}")
//            emit(ResultState.error(e.message ?: "i don't know why"))
        }
    }
}
