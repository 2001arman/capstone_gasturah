package com.gasturah.ui.home

import ApiConfig
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.gasturah.data.util.ModelPreferencesManager
import com.gasturah.databinding.FragmentHomeBinding
import com.gasturah.model.UserModel
import com.gasturah.response.SejarahResponse
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
        showLoading(false)

        val user = ModelPreferencesManager.get<UserModel>("user")
        getDataSejarah()
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
//                addItemDecoration(
//                    DividerItemDecoration(
//                        context,
//                        layoutManager.orientation
//                    )
//                )
            }
            profileSection.tvName.text = user!!.name
            profileSection.tvLevel.text = user!!.level
            Glide.with(this@HomeFragment)
                .load(baseurl + user!!.profile_picture)
                .into(profileSection.imgProfile)
        }
        binding.recyclerPosting.adapter = RecyclerPostingAdapter()
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
                            recyclerRekomendasi.adapter = RecyclerSejarahAdapter(response.body()!!.content)
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