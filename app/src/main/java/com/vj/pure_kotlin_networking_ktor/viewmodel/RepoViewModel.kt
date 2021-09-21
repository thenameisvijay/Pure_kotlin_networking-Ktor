package com.vj.pure_kotlin_networking_ktor.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vj.pure_kotlin_networking_ktor.model.GitRepository
import com.vj.pure_kotlin_networking_ktor.network.ApiCallState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RepoViewModel @Inject constructor(private val gitRepository: GitRepository) : ViewModel() {

    private val mutableStateFlow: MutableStateFlow<ApiCallState> = MutableStateFlow(ApiCallState.Empty)
    val apiStateFlow: StateFlow<ApiCallState> = mutableStateFlow

    fun getRepoData() = viewModelScope.launch {
        gitRepository.getRepoData()
            .onStart {
                mutableStateFlow.value = ApiCallState.Loading
            }
            .catch { e ->
                mutableStateFlow.value = ApiCallState.Failure(e)
            }.collect { response ->
                mutableStateFlow.value = ApiCallState.Success(response)
            }
    }
}