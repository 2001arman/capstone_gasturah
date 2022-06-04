package com.gasturah.data.util

import android.app.Activity
import androidx.appcompat.app.AlertDialog
import com.gasturah.R

class Loading(private val activity: Activity) {
    private lateinit var dialog: AlertDialog
    fun showLoading(){

        val inflater    = activity.layoutInflater
        val dialogView  = inflater.inflate(R.layout.loading,null)
        val builder     = AlertDialog.Builder(activity)

        builder.setView(dialogView)
        builder.setCancelable(false)
        dialog = builder.create()
        dialog.show()
    }
    fun dismissLoading(){
        dialog.dismiss()
    }
}