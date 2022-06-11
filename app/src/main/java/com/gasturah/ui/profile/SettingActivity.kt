package com.gasturah.ui.profile

import ApiConfig
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Base64
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.signature.ObjectKey
import com.gasturah.MainActivity
import com.gasturah.R
import com.gasturah.data.util.Loading
import com.gasturah.data.util.ModelPreferencesManager
import com.gasturah.data.util.uriToFile
import com.gasturah.databinding.ActivitySettingBinding
import com.gasturah.model.UserModel
import com.gasturah.response.UpdateProfileResponse
import com.gasturah.ui.login.LoginActivity
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.InputStream


class SettingActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingBinding

    private var getFile: File? = null
    private val baseurl: String = ApiConfig.baseUrl

    private val loading = Loading(this)

    private var encodedImage: String = ""

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
            .signature(ObjectKey(System.currentTimeMillis()))
            .circleCrop()
            .into(binding.imageProfile)
    }

    private fun setupAction() {
        binding.iconBack.setOnClickListener {
            finish()
        }

        binding.imageProfile.setOnClickListener {
            startGallery()
        }

        binding.btnUpdateProfile.setOnClickListener {
            updatePhoto()
        }

        binding.btnLogout.setOnClickListener {
            ModelPreferencesManager.preferences.edit().remove("user").commit()
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent);
            finish()
        }
    }

    private fun updatePhoto() {
        loading.showLoading()
        val user = ModelPreferencesManager.get<UserModel>("user")

        val username_awal = user?.username.toString().toRequestBody("text/plain".toMediaType())
        val username_baru = binding.etUsername.text.toString().toRequestBody("text/plain".toMediaType())
        val nama          = binding.etName.text.toString().toRequestBody("text/plain".toMediaType())
        val password      = binding.etPassword.text.toString().toRequestBody("text/plain".toMediaType())
        val profile_picture         = encodedImage.toRequestBody("text/plain".toMediaType())

        ApiConfig.getApiService().updateProfile(username_awal, username_baru, nama, password, profile_picture)
            .enqueue(object : Callback<UpdateProfileResponse> {
                override fun onResponse(
                    call: Call<UpdateProfileResponse>,
                    response: Response<UpdateProfileResponse>
                ) {
                    loading.dismissLoading()
                    val respond = response.body()
                    if (response.isSuccessful) {
                        if(respond != null) {
                            Toast.makeText(this@SettingActivity, "Success : ${respond.msg}", Toast.LENGTH_SHORT).show()
                            val username        = respond.content.username
                            val name            = respond.content.name
                            val profile_picture = respond.content.profilePicture
                            val level   = respond.content.level
                            val user    = UserModel(username, name, profile_picture, level)

                            ModelPreferencesManager.put(user, "user")
                            startActivity(Intent(this@SettingActivity, MainActivity::class.java))
                            finish()
                        }
                    }
                }

                override fun onFailure(call: Call<UpdateProfileResponse>, t: Throwable) {
                    loading.dismissLoading()
                    Toast.makeText(this@SettingActivity, "Error : ${t.message}", Toast.LENGTH_SHORT).show()
                }

            })
    }

    private fun startGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, getString(R.string.choose_a_picture))
        launcherIntentGallery.launch(chooser)
    }

    private fun encodeImage(bm: Bitmap): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bm.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val b: ByteArray = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(b, Base64.DEFAULT)
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg: Uri = result.data?.data as Uri
            val imageStream: InputStream? = contentResolver.openInputStream(selectedImg)
            val selectedImage = BitmapFactory.decodeStream(imageStream)
            encodedImage = encodeImage(selectedImage)

            val myFile = uriToFile(selectedImg, this@SettingActivity)

            getFile = myFile

            Glide.with(this).load(selectedImg).circleCrop().into(binding.imageProfile)
        }
    }
}