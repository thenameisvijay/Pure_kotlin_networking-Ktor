package com.vj.pure_kotlin_networking_ktor.view

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.vj.pure_kotlin_networking_ktor.databinding.ActivityMainBinding
import com.vj.pure_kotlin_networking_ktor.network.ApiCallState
import com.vj.pure_kotlin_networking_ktor.viewmodel.RepoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val repoViewModel: RepoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initGetRepoData()
        initViewModelListener()
    }

    private fun initGetRepoData() {
        repoViewModel.getRepoData()
    }

    private fun initViewModelListener() {
        repoViewModel.apiStateFlow.observe(this, {
            Log.e("initViewModelListener", it.toString())

            when (it) {
                is ApiCallState.Loading -> {
                    binding.apply {
                        progressBar.isVisible = true
                        recyclerView.isVisible = false
                        errorMsg.isVisible = false
                    }
                }
                is ApiCallState.Success -> {
                    binding.apply {
                        progressBar.isVisible = false
                        recyclerView.isVisible = true
                        errorMsg.isVisible = false
                    }
                    Log.d("main", "handleResponse: ${it.response}")
//                    postAdapter.submitList(it.response)
                }
                is ApiCallState.Failure -> {
                    binding.apply {
                        progressBar.isVisible = false
                        recyclerView.isVisible = false
                        errorMsg.isVisible = true
                        errorMsg.text = it.error.toString()
                    }

                }
                is ApiCallState.Empty -> {

                }
            }

        })
    }
}