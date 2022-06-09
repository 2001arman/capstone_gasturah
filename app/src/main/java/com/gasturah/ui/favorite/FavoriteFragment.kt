package com.gasturah.ui.favorite

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gasturah.data.database.DaoFavorite
import com.gasturah.data.database.FavoriteData
import com.gasturah.databinding.FragmentFavoriteBinding
import com.gasturah.ui.main.DetailActivity

class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private lateinit var adapter: FavoriteAdapter
    private lateinit var viewModelFavorite: FavoriteViewModel

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
        val recyclerView: RecyclerView = binding.recyclerPosting
        adapter = FavoriteAdapter()
        adapter.notifyDataSetChanged()
        adapter.setOnItemClickCallback(object : FavoriteAdapter.OnItemClickCallback{
            override fun onItemClicked(data: FavoriteData) {
                Intent(requireContext(), DetailActivity::class.java).also {
//                    it.putExtra("DATA", data)
                    startActivity(it)
                }
            }
        })
        binding.apply {
            recyclerView.setHasFixedSize(true)
            recyclerView.layoutManager  = LinearLayoutManager(requireContext())
            recyclerView.adapter        = adapter
        }
        viewModelFavorite.getFavorite()?.observe(requireActivity(), {
            if (it != null) {
                val list = viewList(it)
                adapter.setList(list)
            }
        })
    }

    private fun viewList(favData: List<FavoriteData>): ArrayList<FavoriteData> {
        val listFavUsers = java.util.ArrayList<FavoriteData>()
        favData.forEach {
            val list = FavoriteData(it.name, it.avatar, it.description, it.latitude, it.longitude)
            listFavUsers.add(list)
        }
        return listFavUsers
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}