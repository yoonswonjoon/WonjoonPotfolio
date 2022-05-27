package com.vlm.wonjoonpotfolio.domain

import androidx.compose.ui.graphics.Color

fun String.commaSplit() = this.split(",")

fun String.addUriToString(): ProjectStringType {
    return if(StringConnectList.uriMap.keys.contains(this)){
        try {
            ProjectStringType.LinkedString(
                name = this,
                uri = StringConnectList.uriMap[this],
                color = StringConnectList.colorMap[this] ?: Color.Black
            )
        }catch (e:Exception){
            ProjectStringType.LinkedString(
                name = this,
                uri = StringConnectList.uriMap[this],
                color = StringConnectList.colorMap[this] ?: Color.Black
            )
        }
    }else{
        ProjectStringType.NormalText(
            name = this
        )
    }
}



