package com.example.appgithubuser.ui

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.appgithubuser.data.response.ListResponse
import com.example.appgithubuser.databinding.ListItemBinding

class UserListAdapter :
    ListAdapter<ListResponse, UserListAdapter.UserViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding =
            ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
    }

    inner class UserViewHolder(private val binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(user: ListResponse) {
            binding.apply {
                val userRole = "GitHub ${user.type}"
                tvUsername.text = user.login
                tvName.text = userRole

                ivUserGitHub.load(user.avatarUrl) {
                    crossfade(true)
                    transformations(CircleCropTransformation())
                }
                itemView.setOnClickListener {
                    val context = itemView.context
                    val intent = Intent(context, DetailActivity::class.java)
                    intent.putExtra(EXTRA_LOGIN, user.login)
                    context.startActivity(intent)
                }
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListResponse>() {
            override fun areItemsTheSame(
                oldItem: ListResponse,
                newItem: ListResponse
            ): Boolean {
                return oldItem.login == newItem.login
            }

            override fun areContentsTheSame(
                oldItem: ListResponse,
                newItem: ListResponse
            ): Boolean {
                return oldItem == newItem
            }
        }
        const val EXTRA_LOGIN = "extra_login"
    }
}
