package com.gasturah.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gasturah.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
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