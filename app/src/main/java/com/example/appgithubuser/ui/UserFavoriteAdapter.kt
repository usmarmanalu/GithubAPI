package com.example.appgithubuser.ui

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.appgithubuser.data.local.entity.FavoriteUserEntity
import com.example.appgithubuser.databinding.ListItemBinding

class UserFavoriteAdapter :
    ListAdapter<FavoriteUserEntity, UserFavoriteAdapter.UserFavoriteViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserFavoriteViewHolder {
        val binding =
            ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserFavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserFavoriteViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
    }

    inner class UserFavoriteViewHolder(private val binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(user: FavoriteUserEntity) {
            binding.apply {
                tvUsername.text = user.login

                ivUserGitHub.load(user.avatarUrl) {
                    crossfade(true)
                    transformations(CircleCropTransformation())
                }
                itemView.setOnClickListener {
                    val context = itemView.context
                    val intent = Intent(context, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.EXTRA_LOGIN, user.login)
                    context.startActivity(intent)
                }
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FavoriteUserEntity>() {
            override fun areItemsTheSame(
                oldItem: FavoriteUserEntity,
                newItem: FavoriteUserEntity
            ): Boolean {
                return oldItem.login == newItem.login
            }

            override fun areContentsTheSame(
                oldItem: FavoriteUserEntity,
                newItem: FavoriteUserEntity
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
