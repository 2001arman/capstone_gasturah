package com.gasturah.ui.home

import ApiConfig
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gasturah.databinding.LayoutCardSejarahBinding
import com.gasturah.response.ContentItem

class RecyclerSejarahAdapter(private val listSejarah: List<ContentItem>) :  RecyclerView.Adapter<RecyclerSejarahAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: LayoutCardSejarahBinding) : RecyclerView.ViewHolder(binding.root)
    private val baseurl: String = ApiConfig.baseUrl

    private var onItemClickCallback: OnItemClickCallback? = null

    interface OnItemClickCallback{
        fun OnItemClicked(contentItem: ContentItem, layoutCardSejarahBinding: LayoutCardSejarahBinding)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerSejarahAdapter.ViewHolder {
        val binding =
        LayoutCardSejarahBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerSejarahAdapter.ViewHolder, position: Int) {
        val sejarah = listSejarah[position]
        holder.apply {
            binding.apply {
                Glide.with(this.root)
                    .load(baseurl + sejarah.foto)
                    .into(imgSejarah)
                tvNamaSejarah.text = sejarah.nama
            }
            itemView.setOnClickListener {
                onItemClickCallback?.OnItemClicked(sejarah, binding)
            }
        }

    }


    override fun getItemCount() = listSejarah.size
}