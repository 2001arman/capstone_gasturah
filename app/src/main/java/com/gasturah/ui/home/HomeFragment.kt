package com.gasturah.ui.home

import ApiConfig
import android.R
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.signature.ObjectKey
import com.gasturah.MainActivity
import com.gasturah.data.util.ModelPreferencesManager
import com.gasturah.databinding.FragmentHomeBinding
import com.gasturah.databinding.LayoutCardSejarahBinding
import com.gasturah.model.UserModel
import com.gasturah.response.ContentItem
import com.gasturah.response.PostinganResponse
import com.gasturah.response.SejarahResponse
import com.gasturah.ui.main.DetailActivity
import com.gasturah.ui.profile.SettingActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val baseurl: String = ApiConfig.baseUrl



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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
        showLoading(false)

        val user = ModelPreferencesManager.get<UserModel>("user")
        getDataSejarah()
        getDataPostingan()
        val horizontalLayoutManagaer =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
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
            recyclerRekomendasi.apply {
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
            Glide.with(this@HomeFragment)
                .load(baseurl + user.profile_picture)
                .signature(ObjectKey(System.currentTimeMillis()))
                .circleCrop()
                .placeholder(imgLoading)
                .into(profileSection.imgProfile)
        }

        binding.profileSection.btnSetting.setOnClickListener {
            val moveToSetting = Intent(this.requireActivity(), SettingActivity::class.java)
            startActivity(moveToSetting)
        }

    }

    private fun getDataPostingan(){
        showLoading(true)
        ApiConfig.getApiService().getAllPosting().enqueue(object : Callback<PostinganResponse> {
            override fun onResponse(call: Call<PostinganResponse>, response: Response<PostinganResponse>) {
                showLoading(false)
                if (response.isSuccessful) {
                    if(response.body()!!.status == "success"){
                        binding.apply {
                            recyclerPosting.adapter = RecyclerPostingAdapter(response.body()!!.content).apply {}
                        }
                    } else{
                        Toast.makeText(context, "${response.body()!!.msg}",
                            Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(context, "${response.body()}",
                        Toast.LENGTH_LONG).show();
                }
            }

            override fun onFailure(call: Call<PostinganResponse>, t: Throwable) {
                Toast.makeText(context, "${t.message}",
                    Toast.LENGTH_LONG).show();
            }
        })
    }

    private fun getDataSejarah(){
        showLoading(true)
        ApiConfig.getApiService().getAllSejarah().enqueue(object : Callback<SejarahResponse> {
            override fun onResponse(call: Call<SejarahResponse>, response: Response<SejarahResponse>) {
                showLoading(false)
                if (response.isSuccessful) {
                    if(response.body()!!.status == "success"){
                        Toast.makeText(context, "${response.body()!!.msg}",
                            Toast.LENGTH_LONG).show();
                        Log.d("TAG", "DATA SEJARAH ${response.body()!!.content}")
                        Log.d("TAG", "JUMLAH DATA SEJARAH ${response.body()!!.content.size}")

                        binding.apply {
                            recyclerRekomendasi.adapter = RecyclerSejarahAdapter(response.body()!!.content).apply {
                                setOnItemClickCallback(object : RecyclerSejarahAdapter.OnItemClickCallback{
                                    override fun OnItemClicked(
                                        contentItem: ContentItem,
                                        layoutCardSejarahBinding: LayoutCardSejarahBinding
                                    ) {
                                        val dataSejarah : ContentItem = contentItem
                                        val moveToDetail = Intent(activity, DetailActivity::class.java )
                                        moveToDetail.putExtra(MainActivity.DATA, dataSejarah)
                                        startActivity(moveToDetail)
                                    }

                                })
                            }
                        }
                    } else{
                        Toast.makeText(context, "${response.body()!!.msg}",
                            Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(context, "${response.body()}",
                        Toast.LENGTH_LONG).show();
                }
            }

            override fun onFailure(call: Call<SejarahResponse>, t: Throwable) {
                Toast.makeText(context, "${t.message}",
                    Toast.LENGTH_LONG).show();
            }
        })
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


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}