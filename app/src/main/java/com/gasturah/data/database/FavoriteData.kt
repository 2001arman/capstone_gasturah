package com.gasturah.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tb_favorite")
data class FavoriteData (

    @PrimaryKey
    @ColumnInfo(name = "title")
    val name: String,

    @ColumnInfo(name = "photo")
    val avatar: String,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "latitude")
    val latitude: Double,

    @ColumnInfo(name = "longitude")
    val longitude: Double,
)

