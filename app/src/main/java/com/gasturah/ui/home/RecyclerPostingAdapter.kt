package com.gasturah.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gasturah.R
import com.gasturah.databinding.LayoutPostingImageBinding
import com.gasturah.databinding.LayoutProfileSectionBinding

class RecyclerPostingAdapter : RecyclerView.Adapter<RecyclerPostingAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: LayoutPostingImageBinding) : RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerPostingAdapter.ViewHolder {
        val binding =
            LayoutPostingImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    inner class LandmarkWithImageViewHolder(private val profileSectionBinding: LayoutProfileSectionBinding) :
        RecyclerView.ViewHolder(profileSectionBinding.root) {
    }

    val username = listOf<String>(
        "Arman Maulana", "Eko Darmawan", "Daffa Maffazi"
    )
    override fun onBindViewHolder(holder: RecyclerPostingAdapter.ViewHolder, position: Int) {
        val user = username[position]
        holder.apply {
            binding.apply {
                tvUsername.setText(user)
                imagePosting.setImageResource(R.drawable.lawangsewu)
                imgAvatarUser.setImageResource(R.drawable.rekomendasi_borobudur)
            }
        }
    }

    override fun getItemCount() = username.size

}