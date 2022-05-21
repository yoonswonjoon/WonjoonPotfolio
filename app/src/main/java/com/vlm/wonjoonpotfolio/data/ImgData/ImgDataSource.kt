package com.vlm.wonjoonpotfolio.data.ImgData

import android.net.Uri
import com.vlm.wonjoonpotfolio.domain.ResultState
import kotlinx.coroutines.flow.Flow

interface ImgDataSource {
    suspend fun getImageUpgrade(path : String) : Uri?
}