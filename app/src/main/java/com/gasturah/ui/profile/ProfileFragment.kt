package com.gasturah.ui.profile

import ApiConfig
import android.R
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.signature.ObjectKey
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
        val user = ModelPreferencesManager.get<UserModel>("user")
        val horizontalLayoutManagaer =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.apply {
            recyclerPosting.apply {
                setHasFixedSize(true)
                this.layoutManager = horizontalLayoutManagaer

            }
            profileSection.tvName.text = user!!.name
            profileSection.tvLevel.text = user.level

            val imgLoading = CircularProgressDrawable(requireContext())
            imgLoading.setColorSchemeColors(
                R.color.holo_orange_light,
                R.color.holo_orange_dark,
                R.color.system_accent1_400
            )
            imgLoading.centerRadius = 30f
            imgLoading.strokeWidth = 5f
            imgLoading.start()

            Glide.with(this@ProfileFragment)
                .load(baseurl + user.profile_picture)
                .signature(ObjectKey(System.currentTimeMillis()))
                .placeholder(imgLoading)
                .circleCrop()

                .into(profileSection.imgProfile)
            binding.recyclerPosting.adapter = RecyclerPostingAdapter()
        }
        binding.profileSection.btnSetting.setOnClickListener {
            val moveToSetting = Intent(this.requireActivity(), SettingActivity::class.java)
            startActivity(moveToSetting)
        }
    }
}