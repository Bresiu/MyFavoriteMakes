package com.android.favoritemakes.utilities.result


sealed class Result<out T : Any> {
    class Success<out T : Any>(val result: T) : Result<T>()
    class Failure(val message: String) : Result<Nothing>()
}