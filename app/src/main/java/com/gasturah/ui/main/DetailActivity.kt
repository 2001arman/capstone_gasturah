package com.gasturah.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.gasturah.databinding.ActivityDetailBinding
import com.gasturah.response.ContentItem
import com.gasturah.response.ContentRecognize
import com.gasturah.ui.favorite.FavoriteViewModel
import com.gasturah.ui.share.ShareActivity

class DetailActivity : AppCompatActivity() {

    private val baseurl: String = ApiConfig.baseUrl

    private lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

//        ViewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)
        val id = intent.getParcelableExtra<ContentItem>(DATA) as ContentItem
        getDetail(id)
        setupAction(id)
    }

    private fun setupAction(id: ContentItem) {
        binding.btnToLocation.setOnClickListener {

        }
        binding.iconBack.setOnClickListener{
            finish()
        }

        binding.iconShare.setOnClickListener{
            val intent = Intent(this@DetailActivity, ShareActivity::class.java)
            startActivity(intent)
        }
    }

    private fun getDetail(id: ContentItem) {
        Glide.with(this).load(baseurl+"${id.foto}").into(binding.imgMain)
        binding.textDescription.text    = id.detail
        binding.textSumber.text         = id.sumber
        binding.tvNamaTempat.text = id.nama

    }

    companion object {
        val DATA = "DATA"
        lateinit var ViewModel : FavoriteViewModel
        private var favorite = 0
    }
}