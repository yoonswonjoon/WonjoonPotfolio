package com.vlm.wonjoonpotfolio.domain

import androidx.compose.ui.graphics.Color
import com.vlm.wonjoonpotfolio.ui.component.*

object StringConnectList {
    val uriMap = mapOf<String, String>(
        "MVVM" to "https://doojuns-ordinary.tistory.com/category/%EC%95%88%EB%93%9C%EB%A1%9C%EC%9D%B4%EB%93%9C%20%EC%9D%BD%EC%96%B4%EB%B3%B4%EA%B8%B0/5.%20Android%20Architecture",
        "Coroutine" to "",
        "Compose" to "https://doojuns-ordinary.tistory.com/category/%EC%95%88%EB%93%9C%EB%A1%9C%EC%9D%B4%EB%93%9C%20%EC%9D%BD%EC%96%B4%EB%B3%B4%EA%B8%B0/4.Compose"
    )
    val colorMap = mapOf<String, Pair<Color,Color>>(
        "MVVM" to Pair(BlueColor , AntiBlueColor),
        "Coroutine" to Pair(PinkColor, AntiPinkColor),
        "Compose" to Pair(IvoryColor, AntiIvoryColor)
    )
}