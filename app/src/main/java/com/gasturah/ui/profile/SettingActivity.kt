package com.gasturah.ui.profile

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.gasturah.R
import com.gasturah.data.util.ModelPreferencesManager
import com.gasturah.data.util.uriToFile
import com.gasturah.databinding.ActivitySettingBinding
import com.gasturah.model.UserModel
import java.io.File

class SettingActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingBinding

    private var getFile: File? = null
    private val baseurl: String = ApiConfig.baseUrl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)

        setContentView(binding.root)

        setupView()
        setupAction()
    }

    private fun setupView() {
        val user = ModelPreferencesManager.get<UserModel>("user")

        binding.etName.setText(user?.name)
        binding.etUsername.setText(user?.username)
        Glide.with(this)
            .load(baseurl + user?.profile_picture)
            .into(binding.imageProfile)
    }

    private fun setupAction() {
        binding.iconBack.setOnClickListener {
            finish()
        }

        binding.imageProfile.setOnClickListener {
            startGallery()
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

            val myFile = uriToFile(selectedImg, this@SettingActivity)

            getFile = myFile

            Glide.with(this).load(selectedImg).circleCrop().into(binding.imageProfile)
        }
    }
}