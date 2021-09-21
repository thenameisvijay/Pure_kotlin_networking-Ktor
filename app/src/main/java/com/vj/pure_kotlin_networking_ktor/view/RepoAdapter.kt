package com.vj.pure_kotlin_networking_ktor.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vj.pure_kotlin_networking_ktor.databinding.LayoutItemBinding
import com.vj.pure_kotlin_networking_ktor.model.RepoResponse

class RepoAdapter :
    RecyclerView.Adapter<RepoAdapter.DataViewHolder>() {

    private val gitUsers: ArrayList<RepoResponse> = arrayListOf()

    class DataViewHolder(itemView: LayoutItemBinding) : RecyclerView.ViewHolder(itemView.root) {
        var layoutItemBinding: LayoutItemBinding = itemView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder =
        DataViewHolder(
            LayoutItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.layoutItemBinding.repoList = gitUsers[position]
        holder.layoutItemBinding.executePendingBindings()
    }

    override fun getItemCount(): Int = gitUsers.size

    fun addGitUsers(gitUsers: ArrayList<RepoResponse>) {
        this.gitUsers.apply {
            clear()
            addAll(gitUsers)
        }
    }
}