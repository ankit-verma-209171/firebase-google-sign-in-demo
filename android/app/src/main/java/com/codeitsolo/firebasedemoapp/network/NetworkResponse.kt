package com.codeitsolo.firebasedemoapp.network

sealed class NetworkResponse<out T : Any> {
    data class Success<out T : Any>(val data: T) : NetworkResponse<T>()
    data class Error(val code: Int, val message: String?) : NetworkResponse<Nothing>()
    data class Exception(val e: Throwable) : NetworkResponse<Nothing>()
}
