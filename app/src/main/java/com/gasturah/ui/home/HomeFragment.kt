package com.gasturah.ui.home

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gasturah.databinding.FragmentHomeBinding
import com.gasturah.ui.home.RecyclerHomeAdapter


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
//    private val recyclerView = RecyclerView()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
//        recyclerView = binding.recyclerPosting;
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
//        recyclerView.setAdapter(new RandomNumListAdapter(1234));

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("TAG", "TES DATA ON VIEW CREATED")
        val layoutManager = if (activity?.applicationContext
                ?.resources?.configuration?.orientation == Configuration.ORIENTATION_PORTRAIT) {
            LinearLayoutManager(context)
        } else {
            GridLayoutManager(context, 2)
        }
        binding.apply {
            recyclerPosting.apply {
                setHasFixedSize(true)
//                isNestedScrollingEnabled = false
                this.layoutManager = layoutManager
                addItemDecoration(
                    DividerItemDecoration(
                        context,
                        layoutManager.orientation
                    )
                )
            }
        }
        binding.recyclerPosting.adapter = RecyclerHomeAdapter()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}