package com.vlm.wonjoonpotfolio.data.ImgData

import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import com.vlm.wonjoonpotfolio.domain.ResultState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.lang.Exception
import javax.inject.Inject


class ImgDataSourceImpl @Inject constructor(private val fireStorage : FirebaseStorage) : ImgDataSource{
    override fun getImage(path: String): Flow<ResultState<Uri?>>  = flow{
        try {
            emit(ResultState.loading())
            val uri = fireStorage.reference.child(path).downloadUrl.await()
            emit(ResultState.success(uri))
//            fireStorage.reference.child(path).downloadUrl.addOnSuccessListener {
//                emit(ResultState.success(it))
//            }.addOnFailureListener {
//                emit(ResultState.error(it.message?: "I don't know why"))
//            }.await()
        }catch (e:Exception){
            emit(ResultState.error(e.message?: "I don't know why"))
        }
    }

    override suspend fun getImageUpgrade(path: String): Uri? {
        val uri = fireStorage.reference.child(path).downloadUrl.await()
        return uri
    }
}