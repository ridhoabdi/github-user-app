package com.dicoding.submissiongithubapp.ui.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding.submissiongithubapp.data.local.entity.FavoriteEntity
import com.dicoding.submissiongithubapp.data.local.room.FavoriteDao
import com.dicoding.submissiongithubapp.data.local.room.FavoriteDatabase
import com.dicoding.submissiongithubapp.repository.FavoriteRepository

class FavoriteViewModel(application: Application) : ViewModel() {
    private val mFavoriteRepository: FavoriteRepository = FavoriteRepository(application)
    private var favoriteDatabase: FavoriteDatabase? = FavoriteDatabase.getDatabase(application)
    private var favoriteDao: FavoriteDao? = favoriteDatabase?.favoriteDao()

    fun getAllFavorite(): LiveData<List<FavoriteEntity>> = mFavoriteRepository.getAllFavorite()

    fun insert(favoriteEntity: FavoriteEntity) {
        mFavoriteRepository.insert(favoriteEntity)
    }

    fun delete(id: Int) {
        mFavoriteRepository.delete(id)
    }

    fun check(id: Int) = favoriteDao?.check(id)
}