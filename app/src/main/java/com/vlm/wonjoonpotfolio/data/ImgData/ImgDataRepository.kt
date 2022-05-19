package com.vlm.wonjoonpotfolio.data.ImgData

import javax.inject.Inject

class ImgDataRepository @Inject constructor(private val imgDataSource : ImgDataSource) {
    fun getImage(path : String) = imgDataSource.getImage(path)

    suspend fun getImageUpgrade(path : String) = imgDataSource.getImageUpgrade(path)

}