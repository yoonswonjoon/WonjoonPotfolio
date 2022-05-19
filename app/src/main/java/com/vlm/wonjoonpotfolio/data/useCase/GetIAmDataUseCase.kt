package com.vlm.wonjoonpotfolio.data.useCase

import android.net.Uri
import com.vlm.wonjoonpotfolio.data.ImgData.ImgDataRepository
import com.vlm.wonjoonpotfolio.data.iAm.iAmTextData.IAmTextData
import com.vlm.wonjoonpotfolio.data.iAm.iAmTextData.IAmTextDataRepository
import com.vlm.wonjoonpotfolio.domain.ResultState
import com.vlm.wonjoonpotfolio.ui.iAm.IAmMainViewState
import kotlinx.coroutines.flow.*
import java.lang.Exception
import javax.inject.Inject

class GetIAmDataUseCase
@Inject
constructor(
    private val iAmRepository: IAmTextDataRepository,
    private val imgDataRepository: ImgDataRepository
) {
    operator fun invoke(path : String = ""): Flow<Pair<ResultState<IAmTextData>, ResultState<Uri?>>> =
        iAmRepository.getMainView().combine(imgDataRepository.getImage(path)){ textData , img ->
            textData to img
        }
}
