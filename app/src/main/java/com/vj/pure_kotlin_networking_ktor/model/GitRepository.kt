package com.vj.pure_kotlin_networking_ktor.model

import com.vj.pure_kotlin_networking_ktor.network.GithubEndPoint
import javax.inject.Inject

class GitRepository @Inject constructor(private val endPoint: GithubEndPoint) {
    suspend fun callUserData() = endPoint.requestUserData()
    suspend fun callUserDetailsData(userName:String) = endPoint.requestUserDetailsData(userName)
}