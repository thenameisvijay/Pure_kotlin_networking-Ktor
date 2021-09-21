package com.vj.pure_kotlin_networking_ktor.model

import com.vj.pure_kotlin_networking_ktor.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GitRepository @Inject constructor(private val apiService: ApiService) {

    fun getRepoData() : Flow<ArrayList<RepoResponse>> = flow {
        emit(apiService.requestUserData(BuildConfig.SUB_URL_HEAD))
    }.flowOn(Dispatchers.IO)

}