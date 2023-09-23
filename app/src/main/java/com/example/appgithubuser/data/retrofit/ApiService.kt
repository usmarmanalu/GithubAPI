package com.example.appgithubuser.data.retrofit

import com.example.appgithubuser.data.response.DetailResponse
import com.example.appgithubuser.data.response.ListResponse
import com.example.appgithubuser.data.response.SearchResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("/users/{id}/{type}")
    fun getUsers(
        @Path("id") id: String,
        @Path("type") type: String
    ): Call<List<ListResponse>>

    @GET("/users/{id}")
    fun getDetail(
        @Path("id") id: String
    ): Call<DetailResponse>

    @GET("/search/users")
    fun searchUsers(
        @Query("q") query: String
    ): Call<SearchResponse>
}