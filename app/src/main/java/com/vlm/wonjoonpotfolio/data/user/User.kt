package com.vlm.wonjoonpotfolio.data.user

import android.net.Uri

data class User(
    val eid : String = "",
    val name : String = "",
    val nickname: String = "",
    val uri : String = ""
) {
    constructor() : this("")

    fun toUserForUi(uri: Uri?) : UserForUi{
        return UserForUi(
            eid = this.eid,
            name = this.name,
            nickname = this.nickname,
            uri = uri
        )
    }
}

data class UserForUi(
    val eid : String = "",
    val name : String = "",
    val nickname: String = "",
    val uri : Uri? = null
) {

}

