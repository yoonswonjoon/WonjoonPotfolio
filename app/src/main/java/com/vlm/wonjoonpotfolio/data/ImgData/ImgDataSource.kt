package com.vlm.wonjoonpotfolio.data.ImgData

import android.net.Uri
import com.vlm.wonjoonpotfolio.domain.ResultState
import kotlinx.coroutines.flow.Flow

interface ImgDataSource {
    fun getImage(path : String) : Flow<ResultState<Uri?>>

    suspend fun getImageUpgrade(path : String) : Uri?
}