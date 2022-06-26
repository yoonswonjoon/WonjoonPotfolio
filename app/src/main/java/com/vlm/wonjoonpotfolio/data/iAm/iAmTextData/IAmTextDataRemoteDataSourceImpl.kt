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
        println("get iam ${Thread.currentThread()}")
        return iAm!!
    }


    override suspend fun getList(list: List<String>): List<IAmTextData> {
        val count = list.size
        val mainDoc = firebase.collection("iam").document("basicInfo")
        val iAm = mainDoc.get().await().toObject(IAmTextData::class.java)
        return List(count){iAm!! }
    }
}