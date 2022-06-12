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
import com.gasturah.MainActivity
import com.gasturah.R
import com.gasturah.data.util.Loading
import com.gasturah.data.util.ModelPreferencesManager
import com.gasturah.data.util.uriToFile
import com.gasturah.databinding.ActivityShareBinding
import com.gasturah.model.UserModel
import com.gasturah.response.ShareImageResponse
import com.gasturah.response.UpdateProfileResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File


class ShareActivity : AppCompatActivity() {

    private lateinit var binding: ActivityShareBinding

    private var getFile: File? = null
    private var encodedImage: String = ""
    private val loading = Loading(this)

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
        binding.btnPosting.setOnClickListener {
            posting()
        }
    }

    private fun posting(){
        loading.showLoading()
        val user = ModelPreferencesManager.get<UserModel>("user")
        if(encodedImage == ""){
            loading.dismissLoading()
            Toast.makeText(this@ShareActivity, "Harap pilih foto terlebih dahulu", Toast.LENGTH_SHORT).show()
        } else{
            ApiConfig.getApiService().shareImage(user!!.username, encodedImage)
                .enqueue(object : Callback<ShareImageResponse>{
                    override fun onResponse(
                        call: Call<ShareImageResponse>,
                        response: Response<ShareImageResponse>
                    ) {
                        loading.dismissLoading()
                        val respond = response.body()
                        if (response.isSuccessful) {
                            if(respond != null) {
                                Toast.makeText(this@ShareActivity, "Success : ${respond.msg}", Toast.LENGTH_SHORT).show()
                                startActivity(Intent(this@ShareActivity, MainActivity::class.java))
                                finish()
                            }
                        }
                    }

                    override fun onFailure(call: Call<ShareImageResponse>, t: Throwable) {
                        loading.dismissLoading()
                        Toast.makeText(this@ShareActivity, "Error : ${t.message}", Toast.LENGTH_SHORT).show()
                    }
                })
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
            encodedImage = encodeImage(bm)
        }
    }

    private fun encodeImage(bm: Bitmap): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bm.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val b: ByteArray = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(b, Base64.DEFAULT)
    }
}