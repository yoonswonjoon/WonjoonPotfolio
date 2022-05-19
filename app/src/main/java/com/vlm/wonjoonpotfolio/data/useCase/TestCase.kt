package com.vlm.wonjoonpotfolio.data.useCase

import com.vlm.wonjoonpotfolio.data.ImgData.ImgDataRepository
import com.vlm.wonjoonpotfolio.data.iAm.iAmTextData.IAmTextDataRepository
import javax.inject.Inject

class TestCase @Inject
constructor(
    private val imgDataRepository: ImgDataRepository
) {

}