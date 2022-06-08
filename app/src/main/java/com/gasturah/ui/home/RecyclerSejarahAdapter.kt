package com.gasturah.ui.home

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gasturah.R
import com.gasturah.databinding.LayoutCardSejarahBinding
import com.gasturah.response.ContentItem

class RecyclerSejarahAdapter(private val listSejarah: List<ContentItem>) :  RecyclerView.Adapter<RecyclerSejarahAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: LayoutCardSejarahBinding) : RecyclerView.ViewHolder(binding.root)
    private val baseurl: String = ApiConfig.baseUrl

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerSejarahAdapter.ViewHolder {
        val binding =
        LayoutCardSejarahBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerSejarahAdapter.ViewHolder, position: Int) {
        Log.d("TAG", "DATA SEJARAH ADAPTER ${listSejarah[position]}")
        val sejarah = listSejarah[position]
        holder.apply {
            binding.apply {
                Glide.with(this.root)
                    .load(baseurl + sejarah.foto)
                    .into(imgSejarah)
                tvNamaSejarah.text = sejarah.nama
            }
        }
    }

    override fun getItemCount() = listSejarah.size
}