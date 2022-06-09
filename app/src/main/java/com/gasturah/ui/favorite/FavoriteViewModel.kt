package com.gasturah.ui.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gasturah.data.database.DaoFavorite
import com.gasturah.data.database.DbFavorite
import com.gasturah.data.database.FavoriteData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteViewModel (application: Application): AndroidViewModel(application) {

    private var favoriteDB: DbFavorite?  = DbFavorite.getDatabase(application)
    private var favoriteDao: DaoFavorite? = favoriteDB?.favoritedata()

    fun getFavorite(): LiveData<List<FavoriteData>>? {
        return favoriteDao?.GetDataFavorite()
    }
    fun AddFavorite(name: String, photo: String, description: String, latitude: Double, longitude: Double) {
        CoroutineScope(Dispatchers.IO).launch {
            val favoriteData = FavoriteData(name, photo, description, latitude, longitude)
            favoriteDao?.AddFavorite(favoriteData)
        }
    }
    fun DeleteFavorite(id: String) {
        CoroutineScope(Dispatchers.IO).launch {
            favoriteDao?.removeFromFavorite(id)
        }
    }
//    fun checkFavorite(id: Int) = favoriteDao?.checkFavorite(id)
}