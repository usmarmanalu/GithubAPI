package com.example.appgithubuser.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.appgithubuser.data.response.DetailResponse
import com.example.appgithubuser.databinding.ActivityDetailBinding
import com.example.appgithubuser.model.DetailViewModel
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: DetailViewModel
    private lateinit var userLogin: String

    private lateinit var shimmerImage: ShimmerFrameLayout
    private lateinit var shimmerName: ShimmerFrameLayout
    private lateinit var shimmerBio: ShimmerFrameLayout
    private lateinit var shimmerPublic: ShimmerFrameLayout
    private lateinit var shimmerFollowers: ShimmerFrameLayout
    private lateinit var shimmerFollowings: ShimmerFrameLayout

    companion object {
        const val EXTRA_LOGIN = "extra_login"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showLoading(true)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        userLogin = intent.getStringExtra(EXTRA_LOGIN) ?: ""

        viewModel = ViewModelProvider(this)[DetailViewModel::class.java]

        setupViews()
        startShimmerAnimations()
        setupTabLayoutAndViewPager()

        if (userLogin.isNotEmpty()) {
            viewModel.loadUserDetails(userLogin)
            val tabPagerAdapter = TabViewAdapter(this, userLogin)
            binding.viewPager.adapter = tabPagerAdapter
        }

        viewModel.userDetail.observe(this) { user ->
            displayUserDetails(user)
            stopShimmerAnimations()
            showLoading(false)
        }
    }

    private fun displayUserDetails(user: DetailResponse) {
        binding.apply {
            Glide.with(this@DetailActivity)
                .load(user.avatarUrl)
                .circleCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(ivAvatar)

            val name = "${user.name} (${user.login})"
            val repository = "${user.publicRepos}\nRepository"
            val followers = "${user.followers}\nFollowers"
            val followings = "${user.following}\nFollowings"

            tvName.text = name
            tvPublic.text = repository
            tvFollower.text = followers
            tvFollowing.text = followings

            ivWebLink.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(user.htmlUrl))
                startActivity(intent)
            }

            if (user.bio != null) {
                tvbio.text = user.bio
            } else {
                tvbio.visibility = View.GONE
            }
        }
    }

    private fun setupTabLayoutAndViewPager() {
        val viewPager = binding.viewPager
        val tabLayout = binding.tabLayout

        viewPager.adapter = TabViewAdapter(this@DetailActivity, userLogin)

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "Followers"
                1 -> tab.text = "Followings"
            }
        }.attach()
    }

    private fun startShimmerAnimations() {
        shimmerImage.startShimmer()
        shimmerName.startShimmer()
        shimmerBio.startShimmer()
        shimmerPublic.startShimmer()
        shimmerFollowers.startShimmer()
        shimmerFollowings.startShimmer()
    }

    private fun stopShimmerAnimations() {
        shimmerImage.stopShimmer()
        shimmerName.stopShimmer()
        shimmerBio.stopShimmer()
        shimmerPublic.stopShimmer()
        shimmerFollowers.stopShimmer()
        shimmerFollowings.stopShimmer()

        shimmerImage.visibility = View.GONE
        shimmerName.visibility = View.GONE
        shimmerBio.visibility = View.GONE
        shimmerPublic.visibility = View.GONE
        shimmerFollowers.visibility = View.GONE
        shimmerFollowings.visibility = View.GONE
    }

    private fun setupViews() {
        shimmerImage = binding.shimmerAvatar
        shimmerName = binding.shimmerName
        shimmerBio = binding.shimmerBio
        shimmerPublic = binding.shimmerPublic
        shimmerFollowers = binding.shimmerFollower
        shimmerFollowings = binding.shimmerFollowing
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.tvFollowing.visibility = if (isLoading) View.GONE else View.VISIBLE
        binding.tvName.visibility = if (isLoading) View.GONE else View.VISIBLE
        binding.tvPublic.visibility = if (isLoading) View.GONE else View.VISIBLE
        binding.tvFollower.visibility = if (isLoading) View.GONE else View.VISIBLE
    }
}
