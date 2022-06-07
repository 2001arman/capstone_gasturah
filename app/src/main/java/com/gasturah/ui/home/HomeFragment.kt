package com.gasturah.ui.home

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.gasturah.data.util.ModelPreferencesManager
import com.gasturah.databinding.FragmentHomeBinding
import com.gasturah.model.UserModel


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val baseurl: String = ApiConfig.baseUrl

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = if (activity?.applicationContext
                ?.resources?.configuration?.orientation == Configuration.ORIENTATION_PORTRAIT) {
            LinearLayoutManager(context)
        } else {
            GridLayoutManager(context, 2)
        }
        val user = ModelPreferencesManager.get<UserModel>("user")

        binding.apply {
            recyclerPosting.apply {
                setHasFixedSize(true)
                this.layoutManager = layoutManager
                addItemDecoration(
                    DividerItemDecoration(
                        context,
                        layoutManager.orientation
                    )
                )
            }
            profileSection.tvName.text = user!!.name
            profileSection.tvLevel.text = user!!.level
            Glide.with(this@HomeFragment)
                .load(baseurl + user!!.profile_picture)
                .into(profileSection.imgProfile)
        }
        binding.recyclerPosting.adapter = RecyclerHomeAdapter()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}