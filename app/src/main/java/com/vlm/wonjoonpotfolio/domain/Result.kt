package com.vlm.wonjoonpotfolio.domain

sealed class Result<T>(
) {
    data class Success<T>(val data : T) : Result<T>()
    data class Error<T>(val message: String) : Result<T>()
    class Loading<T> : Result<T>()

    companion object{
        fun <T> loading() = Loading<T>()
        fun <T> success(data : T) = Success<T>(data)
        fun <T> error(message : String) = Error<T>(message)
    }
}