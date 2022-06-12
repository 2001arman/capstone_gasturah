package com.gasturah.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gasturah.R
import com.gasturah.databinding.LayoutPostingImageBinding
import com.gasturah.databinding.LayoutProfileSectionBinding
import com.gasturah.response.ContentItem
import com.gasturah.response.ContentPosting

class RecyclerPostingAdapter(private val listPosting: List<ContentPosting>) : RecyclerView.Adapter<RecyclerPostingAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: LayoutPostingImageBinding) : RecyclerView.ViewHolder(binding.root)
    private val baseurl: String = ApiConfig.baseUrl

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerPostingAdapter.ViewHolder {
        val binding =
            LayoutPostingImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: RecyclerPostingAdapter.ViewHolder, position: Int) {
        val postingan = listPosting[position]
        holder.apply {
            binding.apply {
                tvUsername.setText(postingan.user)
                Glide.with(this.root)
                    .load(baseurl + postingan.foto)
                    .into(imagePosting)
                Glide.with(this.root)
                    .load(baseurl + postingan.profilePicture)
                    .into(imgAvatarUser)
            }
        }
    }

    override fun getItemCount() = listPosting.size

}