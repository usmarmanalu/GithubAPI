package com.example.appgithubuser.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.appgithubuser.data.local.entity.FavoriteUserEntity

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(user: FavoriteUserEntity)

    @Delete
    fun delete(user: FavoriteUserEntity)

    @Query("SELECT * from FavoriteUserEntity ORDER BY login ASC")
    fun getAllUsers(): LiveData<List<FavoriteUserEntity>>

}