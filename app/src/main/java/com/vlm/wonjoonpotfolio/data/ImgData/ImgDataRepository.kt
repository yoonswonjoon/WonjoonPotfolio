package com.vlm.wonjoonpotfolio.data.ImgData

import android.net.Uri
import kotlinx.coroutines.withTimeout
import javax.inject.Inject

class ImgDataRepository @Inject constructor(private val imgDataSource : ImgDataSource) {
    suspend fun getImageUpgrade(path : String) : Uri? =
        withTimeout(2000L){
            imgDataSource.getImageUpgrade(path)
        }
}