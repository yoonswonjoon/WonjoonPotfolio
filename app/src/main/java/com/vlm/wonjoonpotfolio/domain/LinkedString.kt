package com.vlm.wonjoonpotfolio.domain

import com.vlm.wonjoonpotfolio.ui.component.RedColor


sealed class ProjectStringType(){
    data class LinkedString(
        val name: String = "",
        val uri: String? = null,
        val color: androidx.compose.ui.graphics.Color = RedColor,
        val antiColor: androidx.compose.ui.graphics.Color = RedColor
    ) : ProjectStringType()

    data class NormalText(
        val name: String = ""
    ) : ProjectStringType()
}

