package com.gasturah.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.gasturah.databinding.ActivityDetailBinding
import com.gasturah.response.ContentItem
import com.gasturah.response.ContentRecognize
import com.gasturah.ui.favorite.FavoriteViewModel

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
//        binding.iconHeart.setOnClickListener {
//            if (favorite.equals(1)) {
//                binding.iconHeart.setImageDrawable(
//                    ContextCompat.getDrawable(
//                        applicationContext,
//                        R.drawable.ic_baseline_favorite_border_24
//                    )
//                )
//                ViewModel.DeleteFavorite(id!!.nama)
//                Toast.makeText(applicationContext, "Unfavorite ${id.nama}", Toast.LENGTH_SHORT).show()
//                favorite = 0
//            } else {
//                binding.iconHeart.setImageDrawable(
//                    ContextCompat.getDrawable(
//                        applicationContext,
//                        R.drawable.ic_baseline_favorite_24
//                    )
//                )
//                if (DATA != null) {
//                    ViewModel.AddFavorite(id.nama, id.foto, id.detail, id.latitude, id.longitude)
//                    Toast.makeText(applicationContext, "Favorite ${id.nama}", Toast.LENGTH_SHORT).show()
//                }
//                favorite = 1
//            }
//        }
    }

    private fun getDetail(id: ContentItem) {
        Glide.with(this).load(baseurl+"${id.foto}").into(binding.imgMain)
        Toast.makeText(this, id.foto, Toast.LENGTH_SHORT).show()
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