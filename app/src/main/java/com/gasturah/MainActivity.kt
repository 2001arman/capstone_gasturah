package com.gasturah

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.gasturah.data.util.rotateBitmap
import com.gasturah.databinding.ActivityMainBinding
import com.gasturah.ui.camera.CameraActivity
import com.gasturah.ui.main.PreviewActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.io.File
import java.io.FileOutputStream


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var getFile: File? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.navView.background = null

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        navView.setupWithNavController(navController)

        setupAction()
    }

    private fun setupAction() {
        binding.fab.setOnClickListener {
            val intent = Intent(this, CameraActivity::class.java)
            launcherIntentCameraX.launch(intent)
        }
    }

    private val launcherIntentCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == CAMERA_X_RESULT) {
            val myFile = it.data?.getSerializableExtra("picture") as File
            val isBackCamera = it.data?.getBooleanExtra("isBackCamera", true) as Boolean

            getFile = myFile
            val result = rotateBitmap(
                BitmapFactory.decodeFile(getFile?.path),
                isBackCamera
            )
            try {
                val filename = "preview.png"
                val stream: FileOutputStream = openFileOutput(filename, Context.MODE_PRIVATE)
                result.compress(Bitmap.CompressFormat.PNG, 100, stream)

                //Cleanup
                stream.close()
                result.recycle()

                //Pop intent
                val moveToPreview = Intent(this@MainActivity, PreviewActivity::class.java)
                moveToPreview.putExtra("photo", filename)
                moveToPreview.putExtra("file", getFile)
                startActivity(moveToPreview)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    companion object {
        const val CAMERA_X_RESULT = 200
        const val DATA = "DATA"
    }
}