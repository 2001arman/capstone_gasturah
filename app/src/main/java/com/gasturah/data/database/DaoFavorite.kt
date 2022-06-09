package com.gasturah.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DaoFavorite {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun AddFavorite(Favoritedata: FavoriteData)

    @Query("SELECT * FROM tb_favorite")
    fun GetDataFavorite(): LiveData<List<FavoriteData>>

    @Query("SELECT count(*) FROM tb_favorite WHERE tb_favorite.title = :id")
    fun checkFavorite(id: String): String

    @Query("DELETE FROM tb_favorite WHERE tb_favorite.title = :id")
    fun removeFromFavorite(id: String): String
}