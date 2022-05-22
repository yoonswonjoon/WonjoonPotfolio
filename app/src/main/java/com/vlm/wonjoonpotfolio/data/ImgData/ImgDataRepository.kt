package com.vlm.wonjoonpotfolio.data.ImgData

import android.net.Uri
import javax.inject.Inject

class ImgDataRepository @Inject constructor(private val imgDataSource : ImgDataSource) {
    suspend fun getImageUpgrade(path : String) : Uri? = imgDataSource.getImageUpgrade(path)
}