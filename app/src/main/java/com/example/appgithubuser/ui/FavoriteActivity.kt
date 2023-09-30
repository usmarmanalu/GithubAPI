package com.example.appgithubuser.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appgithubuser.databinding.ActivityFavoriteBinding
import com.example.appgithubuser.model.UserFavoriteViewModel
import com.example.appgithubuser.model.UserFavoriteViewModelFactory

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var userFavoriteViewModel: UserFavoriteViewModel
    private lateinit var userFavoriteAdapter: UserFavoriteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showLoading(true)

        userFavoriteAdapter = UserFavoriteAdapter()

        binding.rvFavorite.apply {
            layoutManager = LinearLayoutManager(this@FavoriteActivity)
            adapter = userFavoriteAdapter
        }

        val factory = UserFavoriteViewModelFactory.getInstance(application)
        userFavoriteViewModel = ViewModelProvider(this, factory)[UserFavoriteViewModel::class.java]

        userFavoriteViewModel.getAllUsers().observe(this) { favoriteUsers ->
            if (favoriteUsers.isEmpty()) {
                binding.tvNoFavorite.visibility = View.VISIBLE
                binding.rvFavorite.visibility = View.GONE
            } else {
                binding.tvNoFavorite.visibility = View.GONE
                binding.rvFavorite.visibility = View.VISIBLE
            }
            userFavoriteAdapter.submitList(favoriteUsers)
            showLoading(false)
        }

        val dividerItemDecoration = DividerItemDecoration(
            this, LinearLayoutManager.VERTICAL
        )
        binding.rvFavorite.addItemDecoration(dividerItemDecoration)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBarFavorite.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}
