package com.hfad.doodad.model

sealed class Result<out T> {
    data class Success<T>(val data: T) : Result<T>() // with data
    data class Error(val errorMsg: Exception) : Result<Nothing>() //only with error MSG
    object Loading : Result<Nothing>() // Just a Marker

    override fun toString(): String {
        return when(this){  // when with no elseBranch('when' is cool inside sealedClass)
            is Success -> { "Success $data"}
            is Error -> { "Error $errorMsg"}
            is Loading -> { "Is Loading..."}
        }
    }
}

val Result<*>.Succeeded
    get() = this is Result.Success && data != null