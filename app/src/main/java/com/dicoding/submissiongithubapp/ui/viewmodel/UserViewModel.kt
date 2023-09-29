package com.dicoding.submissiongithubapp.ui.viewmodel

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.submissiongithubapp.data.remote.response.DetailUserResponse
import com.dicoding.submissiongithubapp.data.remote.response.User
import com.dicoding.submissiongithubapp.data.remote.response.UserResponse
import com.dicoding.submissiongithubapp.data.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel : ViewModel() {
    val listUser = MutableLiveData<ArrayList<User>>()
    val detailUser = MutableLiveData<DetailUserResponse>()

    fun setSearch(username: String) {
        ApiConfig.getApi()
            .getUser(username)
            .enqueue(object : Callback<UserResponse> {
                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {
                    if (response.isSuccessful) {
                        listUser.postValue(response.body()?.items)
                    }
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    Log.e(TAG, "onFailure: ${t.message}")
                }
            })
    }

    fun setFollowers(username: String) {
        ApiConfig.getApi()
            .getFollowers(username)
            .enqueue(object : Callback<ArrayList<User>> {
                override fun onResponse(
                    call: Call<ArrayList<User>>,
                    response: Response<ArrayList<User>>
                ) {
                    if (response.isSuccessful) {
                        listUser.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                    Log.e(TAG, "onFailure: ${t.message}")
                }
            })
    }

    fun setFollowing(username: String) {
        ApiConfig.getApi()
            .getFollowing(username)
            .enqueue(object : Callback<ArrayList<User>> {
                override fun onResponse(
                    call: Call<ArrayList<User>>,
                    response: Response<ArrayList<User>>
                ) {
                    if (response.isSuccessful) {
                        listUser.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                    Log.e(TAG, "onFailure: ${t.message}")
                }
            })
    }

    fun setDetail(username: String) {
        ApiConfig.getApi()
            .getDetail(username)
            .enqueue(object : Callback<DetailUserResponse> {
                override fun onResponse(
                    call: Call<DetailUserResponse>,
                    response: Response<DetailUserResponse>
                ) {
                    if (response.isSuccessful) {
                        detailUser.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                    Log.e(TAG, "onFailure: ${t.message}")
                }

            })
    }

    fun get(): LiveData<ArrayList<User>> = listUser

    fun getDetail(): LiveData<DetailUserResponse> = detailUser
}