package com.vlm.wonjoonpotfolio.domain

import com.vlm.wonjoonpotfolio.data.user.User

enum class LoginErrorType{
    NotSignedIn,
    OtherError,
    SettingBasicDataFailed
}

data class LoginResult(
    val data : User? = null,
    val error : LoginErrorType? = null
){

}