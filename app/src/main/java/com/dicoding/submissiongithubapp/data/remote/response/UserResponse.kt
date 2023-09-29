package com.dicoding.submissiongithubapp.data.remote.response

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @field:SerializedName("items")
    val items: ArrayList<User>
)
