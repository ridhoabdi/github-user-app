package com.dicoding.submissiongithubapp.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dicoding.submissiongithubapp.data.local.entity.FavoriteEntity

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(favoriteEntity: FavoriteEntity)

    // select query
    @Query("DELETE FROM FavoriteEntity WHERE FavoriteEntity.id = :id")
    fun delete(id: Int): Int

    @Query("SELECT * FROM FavoriteEntity ORDER BY id ASC")
    fun getAllFavorite(): LiveData<List<FavoriteEntity>>

    @Query("SELECT count(*) FROM FavoriteEntity WHERE FavoriteEntity.id = :id")
    fun check(id: Int): Int
}