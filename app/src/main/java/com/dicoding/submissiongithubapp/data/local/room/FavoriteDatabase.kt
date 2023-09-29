package com.dicoding.submissiongithubapp.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dicoding.submissiongithubapp.data.local.entity.FavoriteEntity

@Database(entities = [FavoriteEntity::class], version = 1)
abstract class FavoriteDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao

    companion object {
        @JvmStatic
        fun getDatabase(context: Context): FavoriteDatabase {
            if (INSTANCE == null) {
                synchronized(FavoriteDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        FavoriteDatabase::class.java,
                        "favorite_db"
                    )
                        .build()
                }
            }
            return INSTANCE as FavoriteDatabase
        }

        @Volatile
        private var INSTANCE: FavoriteDatabase? = null
    }
}