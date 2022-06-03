package com.gasturah.ui.main

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gasturah.databinding.ActivityPreviewBinding
import java.io.FileInputStream

class PreviewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPreviewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPreviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getImage()
    }

    private fun getImage() {
        val photo = intent.getStringExtra("photo")
        try {
            val `is`: FileInputStream = openFileInput(photo)
            val bmp: Bitmap = BitmapFactory.decodeStream(`is`)
            `is`.close()
            binding.imagePreview.setImageBitmap(bmp)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    companion object {
        const val CAMERA_X_RESULT = 200
    }
}