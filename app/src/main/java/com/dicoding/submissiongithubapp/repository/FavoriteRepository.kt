package com.dicoding.submissiongithubapp.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.dicoding.submissiongithubapp.data.local.entity.FavoriteEntity
import com.dicoding.submissiongithubapp.data.local.room.FavoriteDao
import com.dicoding.submissiongithubapp.data.local.room.FavoriteDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteRepository(application: Application) {
    private val mFavoriteDao: FavoriteDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteDatabase.getDatabase(application)
        mFavoriteDao = db.favoriteDao()
    }

    fun getAllFavorite(): LiveData<List<FavoriteEntity>> = mFavoriteDao.getAllFavorite()

    fun insert(favoriteEntity: FavoriteEntity) {
        executorService.execute { mFavoriteDao.insert(favoriteEntity) }
    }

    fun delete(id: Int) {
        executorService.execute { mFavoriteDao.delete(id) }
    }

}