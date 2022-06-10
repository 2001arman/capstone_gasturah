package com.gasturah.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.gasturah.data.util.ModelPreferencesManager
import com.gasturah.databinding.FragmentProfileBinding
import com.gasturah.model.UserModel
import com.gasturah.ui.home.RecyclerPostingAdapter

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val baseurl: String = ApiConfig.baseUrl

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        binding.btnLogout.setOnClickListener {
//            ModelPreferencesManager.preferences.edit().remove("user").commit()
//            var intent = Intent(context, LoginActivity::class.java)
//            startActivity(intent);
//            activity?.finish()
//        }
        val user = ModelPreferencesManager.get<UserModel>("user")
        val horizontalLayoutManagaer =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.apply {
            recyclerPosting.apply {
                setHasFixedSize(true)
                this.layoutManager = horizontalLayoutManagaer

            }
            profileSection.tvName.text = user!!.name
            profileSection.tvLevel.text = user!!.level
            Glide.with(this@ProfileFragment)
                .load(baseurl + user!!.profile_picture)
                .into(profileSection.imgProfile)
            binding.recyclerPosting.adapter = RecyclerPostingAdapter()
        }
    }
}