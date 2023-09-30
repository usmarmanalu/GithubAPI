package com.example.appgithubuser.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.example.appgithubuser.R
import com.example.appgithubuser.databinding.ActivityMainBinding
import com.example.appgithubuser.setting.SettingActivity


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var requestType: String = "followers"
    private val userGithub = "defunkt"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.topAppBar)
        findUser()

        binding.topAppBar.setOnMenuItemClickListener { meniItem ->
            when (meniItem.itemId) {
                R.id.setting -> {
                    val intent = Intent(this, SettingActivity::class.java)
                    startActivity(intent)
                    true
                }

                R.id.favorite -> {
                    val intent = Intent(this, FavoriteActivity::class.java)
                    startActivity(intent)
                    true
                }

                else -> false
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        val searchView = binding.topAppBar.menu.findItem(R.id.menu_search)?.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrBlank()) {
                    val searchUserFragment = SearchUserFragment.newInstance(query)
                    val transaction = supportFragmentManager.beginTransaction()
                    transaction.replace(binding.fragmentContainer.id, searchUserFragment)
                    transaction.addToBackStack(null)
                    transaction.commit()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val userListFragment =
                    supportFragmentManager.findFragmentById(R.id.fragment_container) as? ListFragment
                if (newText != null) {
                    userListFragment?.updateSearchQuery(newText)
                }
                return true
            }
        })
        return true
    }

    private fun findUser() {
        val userListFragment = ListFragment.newInstance(userGithub, requestType)
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(binding.fragmentContainer.id, userListFragment)
        transaction.commit()
    }
}