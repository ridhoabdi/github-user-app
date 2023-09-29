package com.dicoding.submissiongithubapp.data.remote.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    // mendapatkan data login, id, avatar
    val login: String,
    val id: Int,
    val avatar_url: String
) : Parcelable
