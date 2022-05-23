package com.gasturah.ui.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gasturah.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
    }

    private fun setupView() {
        supportActionBar?.hide()
    }

    private fun setupAnimation() {

    }

    private fun setupAction() {

    }
}