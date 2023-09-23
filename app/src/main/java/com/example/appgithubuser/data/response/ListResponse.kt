package com.example.appgithubuser.data.response

import com.google.gson.annotations.SerializedName

data class ListResponse (
    @field:SerializedName("avatar_url")
    val avatarUrl: String? = null,

    @field:SerializedName("login")
    val login: String? = null,

    @field:SerializedName("type")
    val type: String? = null
)