package com.gasturah.ui.main

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.gasturah.data.util.Loading
import com.gasturah.databinding.ActivityPreviewBinding
import java.io.FileInputStream

class PreviewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPreviewBinding
    private val loading = Loading(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPreviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        getImage()
        setupAction()
    }

    private fun setupAction() {
        binding.button.setOnClickListener {
            loading.showLoading()
        }
    }

    private fun getImage() {
        val photo = intent.getStringExtra("photo")
        try {
            val `is`: FileInputStream = openFileInput(photo)
            val bmp: Bitmap = BitmapFactory.decodeStream(`is`)
            `is`.close()
            binding.imagePreview.setImageBitmap(bmp)

            Glide.with(this).asBitmap()
                .load(bmp)
                .transform(RoundedCorners(32))
                .into(binding.imagePreview)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    companion object {
        const val CAMERA_X_RESULT = 200
    }
}