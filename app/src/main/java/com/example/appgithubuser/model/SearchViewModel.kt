package com.example.appgithubuser.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appgithubuser.data.response.ItemsItem
import com.example.appgithubuser.data.response.SearchResponse
import com.example.appgithubuser.data.retrofit.ApiConfig
import retrofit2.*

class SearchViewModel : ViewModel() {

    companion object {
        private const val TAG = "SearchViewModel"
    }

    private val _userList = MutableLiveData<List<ItemsItem>>()
    val userList: LiveData<List<ItemsItem>> = _userList

    fun searchUsers(query: String) {
        val client = ApiConfig.getApiService().searchUsers(query)
        client.enqueue(object : Callback<SearchResponse> {
            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
            ) {
                if (response.isSuccessful) {
                    _userList.value = response.body()?.items as List<ItemsItem>?
                } else {
                    Log.e(TAG, "OnFailure : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                Log.e(TAG, "OnFailure : ${t.message}")
            }
        })
    }
}