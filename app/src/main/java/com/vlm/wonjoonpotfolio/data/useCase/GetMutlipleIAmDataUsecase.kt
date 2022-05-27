package com.vlm.wonjoonpotfolio.data.useCase

import com.vlm.wonjoonpotfolio.data.ImgData.ImgDataRepository
import com.vlm.wonjoonpotfolio.data.history.TestHistoryClass
import com.vlm.wonjoonpotfolio.data.iAm.iAmTextData.IAmTextDataRepository
import com.vlm.wonjoonpotfolio.domain.ResultState
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetMutlipleIAmDataUsecase
@Inject
constructor(
    private val iAmRepository: IAmTextDataRepository,
    private val imgDataRepository: ImgDataRepository
)  {
    operator fun invoke(paths : List<String>) = flow {
        try {
            emit(ResultState.loading())
            val resultList = mutableListOf<TestHistoryClass>()
            val datas = iAmRepository.getListMainView(paths)
            datas.forEach {
                resultList.add(TestHistoryClass(data = it.name))
            }

            emit(ResultState.success(resultList))

            datas.forEachIndexed { index , d->
                val uri = imgDataRepository.getImageUpgrade("test.png")
                resultList[index] = resultList[index].copy(uri = uri)
                emit(ResultState.success(resultList))
            }


        }catch (e:Exception){
            emit(ResultState.error(e.message?: "i don't know why"))
        }

    }
}