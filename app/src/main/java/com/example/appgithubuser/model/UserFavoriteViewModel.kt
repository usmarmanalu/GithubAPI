package com.example.appgithubuser.model

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.appgithubuser.data.UserRepository
import com.example.appgithubuser.data.local.entity.FavoriteUserEntity

class UserFavoriteViewModel(application: Application) : ViewModel() {
    private val mUserRepository: UserRepository = UserRepository(application)

    fun getAllUsers(): LiveData<List<FavoriteUserEntity>> = mUserRepository.getAllUsers()

    fun insert(user: FavoriteUserEntity) {
        mUserRepository.insert(user)
    }

    fun delete(user: FavoriteUserEntity) {
        mUserRepository.delete(user)
    }
}