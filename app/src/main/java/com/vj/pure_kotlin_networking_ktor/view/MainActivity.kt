package com.vj.pure_kotlin_networking_ktor.view

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.vj.pure_kotlin_networking_ktor.databinding.ActivityMainBinding
import com.vj.pure_kotlin_networking_ktor.model.RepoResponse
import com.vj.pure_kotlin_networking_ktor.network.ApiCallState
import com.vj.pure_kotlin_networking_ktor.viewmodel.RepoViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val repoViewModel: RepoViewModel by viewModels()
    private lateinit var adapter: RepoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupUI()
        setupObservers()
    }

    private fun setupUI(){
        adapter = RepoAdapter()
        binding.apply {
            recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
            recyclerView.addItemDecoration(
                DividerItemDecoration(
                    recyclerView.context,
                    (recyclerView.layoutManager as LinearLayoutManager).orientation
                )
            )
            recyclerView.adapter = adapter
        }
    }

    private fun setupObservers() {
        repoViewModel.getRepoData()
        lifecycleScope.launch { repoViewModel.apiStateFlow.collect {
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
                    setToAdapter(it.response)
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
                    binding.apply {
                        progressBar.isVisible = false
                        recyclerView.isVisible = false
                        errorMsg.isVisible = true
                        errorMsg.text = it.toString()
                    }
                }
            }
        } }

    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setToAdapter(gitUsers: ArrayList<RepoResponse>) {
        adapter.apply {
            addGitUsers(gitUsers)
            notifyDataSetChanged()
        }
    }
}