package com.vlm.wonjoonpotfolio.data.user

import android.net.Uri

data class User(
    val eid : String = "",
    val name : String = "",
    val nickname: String = "",
    val uri : String = "",
    val phone : String = "",
    val uid : String = ""
) {
    constructor() : this("")

    fun toUserForUi(uri: Uri?) : UserForUi{
        return UserForUi(
            eid = this.eid,
            name = this.name,
            nickname = this.nickname,
            uri = uri,
            phone = this.phone
        )
    }
}

data class UserForUi(
    val eid : String = "",
    val name : String = "",
    val nickname: String = "",
    val uri : Uri? = null,
    val phone : String = ""
) {

}
