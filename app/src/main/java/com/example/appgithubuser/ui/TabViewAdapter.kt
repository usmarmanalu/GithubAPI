package com.example.appgithubuser.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class TabViewAdapter(fragmentActivity: FragmentActivity, private val username: String) :
    FragmentStateAdapter(fragmentActivity) {

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> ListFragment.newInstance(username, "followers")
            1 -> ListFragment.newInstance(username, "following")
            else -> throw IllegalArgumentException("Invalid tab position: $position")
        }
    }

    override fun getItemCount(): Int {
        return 2
    }
}