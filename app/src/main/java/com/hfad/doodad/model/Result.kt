package com.hfad.doodad.model

sealed class Result<out T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Error(val errorMsg: Exception) : Result<Nothing>()
    object Loading : Result<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success -> "Success data = $data"
            is Error -> "Error MSG = $errorMsg"
            is Loading -> "Loading"
        }
    }
}

val Result<*>.Succeeded
    get() = this is Result.Success && data != null