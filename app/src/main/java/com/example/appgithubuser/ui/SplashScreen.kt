package com.example.appgithubuser.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.example.appgithubuser.R
import com.example.appgithubuser.setting.SettingPreferences
import com.example.appgithubuser.setting.SettingViewModel
import com.example.appgithubuser.setting.SettingViewModelFactory
import com.example.appgithubuser.setting.dataStore

class SplashScreen : AppCompatActivity() {

    private val SPLASH_DELAY: Long = 2000

    override fun onCreate(savedInstanceState: Bundle?) {

        val pref = SettingPreferences.getInstance(application.dataStore)

        val viewModel = ViewModelProvider(this, SettingViewModelFactory(pref))[SettingViewModel::class.java]
        viewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler().postDelayed({
            val intent = Intent(
                this, MainActivity::class.java
            )
            startActivity(intent)
            finish()

        }, SPLASH_DELAY)
    }
}