package com.vlm.wonjoonpotfolio.data.iAm

import com.google.firebase.firestore.FirebaseFirestore
import com.vlm.wonjoonpotfolio.domain.ResultState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class IAmFirebaseApi
@Inject
constructor(
    private val firebase : FirebaseFirestore
    )  : IAmRemoteDataSource {
//    val firebase = FirebaseFirestore.getInstance()
        override fun getIam() = flow{
        try {
            emit(ResultState.loading())

            kotlinx.coroutines.delay(1000) // 로딩하는 척 하기
            val mainDoc = firebase.collection("iam").document("basicInfo")
            val iAm = mainDoc.get().await().toObject(IAm::class.java)
            iAm?.let {
                emit(ResultState.success(data = iAm))
            }?: emit(ResultState.success(data = IAm("","","","")))

        }catch (e: Exception){
            emit(ResultState.error(e.message?: "i don't know why"))
        }
    }
}