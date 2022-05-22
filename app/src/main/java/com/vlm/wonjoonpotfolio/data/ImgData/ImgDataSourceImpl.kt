package com.vlm.wonjoonpotfolio.data.ImgData

import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import com.vlm.wonjoonpotfolio.domain.ResultState
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.tasks.asDeferred
import kotlinx.coroutines.tasks.await
import java.lang.Exception
import javax.inject.Inject


class ImgDataSourceImpl @Inject constructor(private val fireStorage : FirebaseStorage) : ImgDataSource{

    override suspend fun getImageUpgrade(path: String): Uri? {
        val uri = fireStorage.reference.child(path).downloadUrl.await()
        return uri
    }

}