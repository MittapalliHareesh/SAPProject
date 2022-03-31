package com.assignment.myapplication1.util

import androidx.lifecycle.MutableLiveData
import com.assignment.myapplication1.Image

//PUtModel class name here
data class Result<out T>(val state: Status, val data: T?, val errorMessage: String?) {
    companion object {
        fun <T> success(data: T): Result<T> {
            return Result(Status.SUCCESS, data = data, null)
        }

        fun <T> loading(data: T?): Result<T> {
            return Result(Status.LOADING, data = data, null)

        }

        fun <T> failure(data: T?, message: String): Result<T> {
            return Result(Status.FAILURE, data = data, errorMessage = message)

        }
    }
}

enum class Status {
    LOADING,
    SUCCESS,
    FAILURE,
}