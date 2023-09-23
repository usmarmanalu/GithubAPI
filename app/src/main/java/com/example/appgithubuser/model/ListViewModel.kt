package com.example.appgithubuser.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appgithubuser.data.response.ListResponse
import com.example.appgithubuser.data.retrofit.ApiConfig
import retrofit2.*

class ListViewModel : ViewModel() {

    companion object {
        private const val TAG = "ListViewModel"
    }

    private val _originalUserList = MutableLiveData<List<ListResponse>>()
    private val _userList = MutableLiveData<List<ListResponse>>()
    val userList: LiveData<List<ListResponse>> = _userList

    fun fetchUserList(username: String, type: String) {
        val client = ApiConfig.getApiService().getUsers(username, type)
        client.enqueue(object : Callback<List<ListResponse>> {
            override fun onResponse(
                call: Call<List<ListResponse>>,
                response: Response<List<ListResponse>>
            ) {
                if (response.isSuccessful) {
                    _userList.value = response.body()
                    _originalUserList.value = response.body()
                } else {
                    Log.e(TAG, "OnFailure : ${response.body()}")
                }
            }

            override fun onFailure(call: Call<List<ListResponse>>, t: Throwable) {
                Log.e(TAG, "OnFailure : ${t.message}")
            }
        })
    }

    fun queryUserListByCriteria(criteria: String) {
        val originalList = _originalUserList.value.orEmpty()
        val filteredList = originalList.filter { user ->
            user.login?.contains(criteria, ignoreCase = true) == true
        }
        _userList.value = filteredList
    }
}