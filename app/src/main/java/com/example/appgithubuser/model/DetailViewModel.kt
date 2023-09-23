package com.example.appgithubuser.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appgithubuser.data.response.DetailResponse
import com.example.appgithubuser.data.retrofit.ApiConfig
import retrofit2.*
class DetailViewModel : ViewModel() {

    companion object {
        private const val TAG = "DetailViewModel"
    }

    private val _userDetail = MutableLiveData<DetailResponse>()
    val userDetail: LiveData<DetailResponse> = _userDetail

    fun loadUserDetails(login: String) {
        val client = ApiConfig.getApiService().getDetail(login)
        client.enqueue(object : Callback<DetailResponse> {
            override fun onResponse(
                call: Call<DetailResponse>,
                response: Response<DetailResponse>
            ) {
                if (response.isSuccessful) {
                    val user = response.body()
                    if (user != null) {
                        _userDetail.postValue(user)
                    } else {
                        Log.e(TAG, "OnFailure : ${response.message()}")
                    }
                }
            }

            override fun onFailure(call: Call<DetailResponse>, t: Throwable) {
                Log.e(TAG, "OnFailure : ${t.message}")
            }
        })
    }
}