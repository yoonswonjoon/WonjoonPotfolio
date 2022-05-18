package com.vlm.wonjoonpotfolio.domain

sealed class ResultState<T>(
) {
    data class Success<T>(val data : T) : ResultState<T>()
    data class Error<T>(val message: String) : ResultState<T>()
    class Loading<T> : ResultState<T>()

    companion object{
        fun <T> loading() = Loading<T>()
        fun <T> success(data : T) = Success<T>(data)
        fun <T> error(message : String) = Error<T>(message)
    }
}