package com.gasturah.ui.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.signature.ObjectKey
import com.gasturah.data.util.Loading
import com.gasturah.data.util.ModelPreferencesManager
import com.gasturah.databinding.FragmentProfileBinding
import com.gasturah.model.UserModel
import com.gasturah.response.PostinganResponse
import com.gasturah.ui.home.RecyclerPostingAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

        showLoading(false)

        val user = ModelPreferencesManager.get<UserModel>("user")
        val username = user!!.username
        getPostingUser(username)

        val horizontalLayoutManagaer =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.apply {
            recyclerPosting.apply {
                setHasFixedSize(true)
                this.layoutManager = horizontalLayoutManagaer

            }
            profileSection.tvName.text = user!!.name
            profileSection.tvLevel.text = user.level
            Glide.with(this@ProfileFragment)
                .load(baseurl + user.profile_picture)
                .signature(ObjectKey(System.currentTimeMillis()))
                .circleCrop()
                .into(profileSection.imgProfile)
        }
        binding.profileSection.btnSetting.setOnClickListener {
            val moveToSetting = Intent(this.requireActivity(), SettingActivity::class.java)
            startActivity(moveToSetting)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.apply {
            if(isLoading){
                homeGroup.visibility = View.INVISIBLE
                homeLoadingGroup.visibility = View.VISIBLE
            } else{
                homeGroup.visibility = View.VISIBLE
                homeLoadingGroup.visibility = View.INVISIBLE
            }

        }
    }

    private fun getPostingUser(username: String){
        showLoading(true)
        ApiConfig.getApiService().getPostingUser(username).enqueue(object : Callback<PostinganResponse> {
            override fun onResponse(call: Call<PostinganResponse>, response: Response<PostinganResponse>) {
                showLoading(false)
                if (response.isSuccessful) {
                    if(response.body()!!.status == "success"){
                        binding.apply {
                            recyclerPosting.adapter = RecyclerPostingAdapter(response.body()!!.content)
                            tvInfo.visibility = View.INVISIBLE
                        }
                    } else{
                        binding.apply {
                            recyclerPosting.visibility = View.INVISIBLE
                        }
                    }
                }
            }

            override fun onFailure(call: Call<PostinganResponse>, t: Throwable) {
                showLoading(false)
                Toast.makeText(context, "${t.message}",
                    Toast.LENGTH_LONG).show();
            }
        })
    }
}