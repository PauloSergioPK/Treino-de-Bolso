package com.karasdecc.treinodebolso.database

sealed class DatabaseResult<out T> {
    class Success<T>(val data: T) : DatabaseResult<T>()
    class Failure<T>(val exception: Throwable) : DatabaseResult<T>()
}