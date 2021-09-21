package com.vj.pure_kotlin_networking_ktor.model

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GitRepository @Inject constructor(private val apiService: ApiService) {

    fun getRepoData() : Flow<ArrayList<RepoResponse>> = flow {
        emit(apiService.requestUserData())
    }.flowOn(Dispatchers.IO)

}