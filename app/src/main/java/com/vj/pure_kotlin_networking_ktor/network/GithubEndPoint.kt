package com.vj.pure_kotlin_networking_ktor.network

import com.vj.pure_kotlin_networking_ktor.model.RepoResponse
import com.vj.pure_kotlin_networking_ktor.model.UserDetailsResponse

interface GithubEndPoint {

    suspend fun requestUserData(): ArrayList<RepoResponse>

//    @GET("users/{userName}")
    fun requestUserDetailsData(@Path("userName") userName: String): UserDetailsResponse
}