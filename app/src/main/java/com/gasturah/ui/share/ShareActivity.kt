package com.gasturah.ui.share

import ApiConfig
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.gasturah.R
import com.gasturah.data.util.uriToFile
import com.gasturah.databinding.ActivityShareBinding
import java.io.ByteArrayOutputStream
import java.io.File


class ShareActivity : AppCompatActivity() {

    private lateinit var binding: ActivityShareBinding

    private var getFile: File? = null
    private val baseurl: String = ApiConfig.baseUrl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShareBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.imgPosting.setOnClickListener {
            startGallery()
        }
        binding.btnCancel.setOnClickListener {
            finish()
        }
    }

    private fun startGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, getString(R.string.choose_a_picture))
        launcherIntentGallery.launch(chooser)
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg: Uri = result.data?.data as Uri

            val myFile = uriToFile(selectedImg, this@ShareActivity)

            getFile = myFile

            Glide.with(this).load(selectedImg).circleCrop().into(binding.imgPosting)


            val bm = BitmapFactory.decodeFile(myFile.toPath().toString())
            val baos = ByteArrayOutputStream()
            bm.compress(Bitmap.CompressFormat.PNG, 100, baos) // bm is the bitmap object

            val b: ByteArray = baos.toByteArray()

            val encodedImage: String = Base64.encodeToString(b, Base64.DEFAULT)
            Log.d("TAG", "Base64: $encodedImage")
        }
    }
}