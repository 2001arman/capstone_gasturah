package com.gasturah.ui.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gasturah.data.database.FavoriteData
import com.gasturah.databinding.FragmentItemBinding

class FavoriteAdapter: RecyclerView.Adapter<FavoriteAdapter.ListViewHolder>() {

    private val listFavoriteData = ArrayList<FavoriteData>()
    private lateinit var onItemClickCallback: FavoriteAdapter.OnItemClickCallback

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ListViewHolder {
        val binding = FragmentItemBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.binding(listFavoriteData[position])
    }

    inner class ListViewHolder (private val binding: FragmentItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun binding(favorite: FavoriteData){
            binding.root.setOnClickListener {
                onItemClickCallback?.onItemClicked(favorite)
            }
            binding.apply {
                Glide.with(itemView)
                    .load(favorite.avatar)
                    .into(imgItemPhoto)
                tvItemName.text = favorite.name
                tvDescript.text = favorite.description
            }
        }
    }

    override fun getItemCount(): Int    = listFavoriteData.size

    fun setList(place: ArrayList<FavoriteData>) {
        listFavoriteData.clear()
        listFavoriteData.addAll(place)
        notifyDataSetChanged()
    }
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: FavoriteData)
    }
}