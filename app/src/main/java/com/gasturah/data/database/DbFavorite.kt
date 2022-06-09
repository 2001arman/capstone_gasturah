package com.gasturah.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FavoriteData::class],version = 1)
abstract class DbFavorite: RoomDatabase() {

    abstract fun favoritedata(): DaoFavorite

    companion object{
        @Volatile
        private var INSTANCE : DbFavorite?=null

        fun getDatabase(context: Context): DbFavorite? {
            if (INSTANCE == null) {
                synchronized(DbFavorite::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext, DbFavorite::class.java, "favorite_database").build()
                }
            }
            return INSTANCE
        }
    }
}