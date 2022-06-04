package com.gasturah.ui.home

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gasturah.R
import com.gasturah.databinding.LayoutPostingImageBinding
import com.gasturah.databinding.LayoutProfileSectionBinding
import de.hdodenhof.circleimageview.CircleImageView

class RecyclerHomeAdapter : RecyclerView.Adapter<RecyclerHomeAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: LayoutPostingImageBinding) : RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerHomeAdapter.ViewHolder {
        val binding =
            LayoutPostingImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    inner class LandmarkWithImageViewHolder(private val profileSectionBinding: LayoutProfileSectionBinding) :
        RecyclerView.ViewHolder(profileSectionBinding.root) {
    }
//    private var username = ArrayList<String>()
//    username.add("Arman Maulana")
//    username.add("Eko Darmawan")
//    username.add("Daffa Mafazi")

    val username = listOf<String>(
        "Arman Maulana", "Eko Darmawan", "Daffa Maffazi"
    )
    override fun onBindViewHolder(holder: RecyclerHomeAdapter.ViewHolder, position: Int) {

        val user = username[position]
        print("TESTES DATA")
        Log.d("TAG","Data $user")
        Log.d("TAG","Jumlah Data ${username.size}")
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