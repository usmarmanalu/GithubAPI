package com.example.appgithubuser.ui

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.appgithubuser.data.response.ItemsItem
import com.example.appgithubuser.databinding.ListItemBinding

class SearchAdapter :
    ListAdapter<ItemsItem, SearchAdapter.UserViewHolder>(DIFF_CALLBACK) {

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

        fun bind(user: ItemsItem) {
            binding.apply {
                val userRole = "GitHub ${user.type}"
                tvName.text = userRole
                tvUsername.text = user.login

                Glide.with(itemView)
                    .load(user.avatarUrl)
                    .circleCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(ivUserGitHub)

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
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ItemsItem>() {
            override fun areItemsTheSame(
                oldItem: ItemsItem,
                newItem: ItemsItem
            ): Boolean {
                return oldItem.login == newItem.login
            }

            override fun areContentsTheSame(
                oldItem: ItemsItem,
                newItem: ItemsItem
            ): Boolean {
                return oldItem == newItem
            }
        }
        private const val EXTRA_LOGIN = "extra_login"
    }
}
