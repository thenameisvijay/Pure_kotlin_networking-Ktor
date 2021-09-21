package com.vj.pure_kotlin_networking_ktor.network

import com.vj.pure_kotlin_networking_ktor.model.RepoResponse

sealed class ApiCallState {
    object Loading : ApiCallState()
    object Empty : ApiCallState()
    class Success(val response: ArrayList<RepoResponse>) : ApiCallState()
    class Failure(val error: Throwable) : ApiCallState()
}