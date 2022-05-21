package com.vlm.wonjoonpotfolio.data.iAm.iAmTextData

import com.google.firebase.firestore.FirebaseFirestore
import com.vlm.wonjoonpotfolio.domain.ResultState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class IAmTextDataRemoteDataSourceImpl
@Inject
constructor(
    private val firebase: FirebaseFirestore
) : IAmTextDataRemoteDataSource {
    override suspend fun getIam(): IAmTextData {
        val mainDoc = firebase.collection("iam").document("basicInfo")
        val iAm = mainDoc.get().await().toObject(IAmTextData::class.java)
        return iAm!!
    }

        /*flow {
        try {
            emit(ResultState.loading())
            kotlinx.coroutines.delay(1000) // 로딩하는 척 하기
            val mainDoc = firebase.collection("iam").document("basicInfo")
            val iAm = mainDoc.get().await().toObject(IAmTextData::class.java)
            iAm?.let {
                emit(ResultState.success(data = iAm))
            } ?: emit(ResultState.success(data = IAmTextData("", "", "", "")))
        } catch (e: Exception) {
            emit(ResultState.error(e.message ?: "i don't know why"))
        }
    }*/

    override suspend fun getList(list: List<String>): List<IAmTextData> {
        val count = list.size
        val mainDoc = firebase.collection("iam").document("basicInfo")
        val iAm = mainDoc.get().await().toObject(IAmTextData::class.java)
        return List(count){iAm!! }
    }
}