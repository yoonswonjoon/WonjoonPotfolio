package com.vlm.wonjoonpotfolio.data.iAm

import com.google.firebase.firestore.FirebaseFirestore
import com.vlm.wonjoonpotfolio.domain.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.lang.Exception
import javax.inject.Inject


class IAmFirebaseApi @Inject constructor(/*private val firebase : FirebaseFirestore*/) : IAmRemoteDataSource {


    //    /*override*/ fun getIam(): Flow<Result<IAm>> = flow{
//        try {
//            emit(Result.loading())
//
//            kotlinx.coroutines.delay(1000) // 로딩하는 척 하기
//            val mainDoc = firebase.collection("iam").document("basicInfo")
//            val iAm = mainDoc.get().await().toObject(IAm::class.java)
//            iAm?.let {
//                emit(Result.success(data = iAm))
//            }?: emit(Result.success(data = IAm("","","","")))
//
//        }catch (e: Exception){
//            emit(Result.error(e.message?: "i don't know why"))
//        }
//    }
    override fun test(): String {
        return ""
    }

}