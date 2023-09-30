package com.example.appgithubuser.data

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.appgithubuser.data.local.entity.FavoriteUserEntity
import com.example.appgithubuser.database.FavoriteDao
import com.example.appgithubuser.database.FavoriteRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class UserRepository(application: Application) {
    private val mUsersDao: FavoriteDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteRoomDatabase.getDatabase(application)
        mUsersDao = db.favoriteDao()
    }

    fun getAllUsers(): LiveData<List<FavoriteUserEntity>> = mUsersDao.getAllUsers()
    fun insert(user: FavoriteUserEntity) {
        executorService.execute { mUsersDao.insert(user) }
    }

    fun delete(user: FavoriteUserEntity) {
        executorService.execute { mUsersDao.delete(user) }
    }
}