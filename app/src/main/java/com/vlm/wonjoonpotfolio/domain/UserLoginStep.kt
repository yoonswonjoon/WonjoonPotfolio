package com.vlm.wonjoonpotfolio.domain

sealed class UserLoginStep<T> {

    data class DataExist<T>(val data: T) : UserLoginStep<T>()
    class DataNull<T>() : UserLoginStep<T>()
    class Loading<T> : UserLoginStep<T>()

    companion object {
        fun <T> loading() = Loading<T>()
        fun <T> dataExist(data: T) = DataExist<T>(data)
        fun <T> dataNull() = DataNull<T>()
    }

}