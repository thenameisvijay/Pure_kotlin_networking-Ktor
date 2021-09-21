package com.vj.pure_kotlin_networking_ktor.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

    private val _apiStateFlow: MutableLiveData<ApiCallState> = MutableLiveData(ApiCallState.Empty)
    val apiStateFlow: LiveData<ApiCallState> = _apiStateFlow

    fun getRepoData() = viewModelScope.launch {
        gitRepository.getRepoData()
            .onStart {
                _apiStateFlow.value = ApiCallState.Loading
            }
            .catch { e ->
                _apiStateFlow.value = ApiCallState.Failure(e)
            }.collect { response ->
                _apiStateFlow.value = ApiCallState.Success(response)
            }
    }
}