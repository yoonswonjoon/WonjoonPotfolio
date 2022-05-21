package com.vlm.wonjoonpotfolio.data.ImgData

import javax.inject.Inject

class ImgDataRepository @Inject constructor(private val imgDataSource : ImgDataSource) {
    suspend fun getImageUpgrade(path : String) = imgDataSource.getImageUpgrade(path)
}