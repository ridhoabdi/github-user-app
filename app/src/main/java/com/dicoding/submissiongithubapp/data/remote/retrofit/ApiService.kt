package com.dicoding.submissiongithubapp.data.remote.retrofit

import com.dicoding.submissiongithubapp.data.remote.response.DetailUserResponse
import com.dicoding.submissiongithubapp.data.remote.response.User
import com.dicoding.submissiongithubapp.data.remote.response.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.ArrayList

interface ApiService {
    // token github
    @Headers("Authorization: token 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")

    // search
    @GET("search/users")
    fun getUser(
        @Query("q") query: String
    ): Call<UserResponse>

    // mencari data username
    @GET("/users/{username}")
    fun getDetail(
        @Path("username") username: String
    ): Call<DetailUserResponse>

    // mencari data followers
    @GET("users/{username}/followers")
    fun getFollowers(
        @Path("username") username: String
    ): Call<ArrayList<User>>

    // mencari data following
    @GET("users/{username}/following")
    fun getFollowing(
        @Path("username") username: String
    ): Call<ArrayList<User>>
}