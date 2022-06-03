package com.gasturah.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.gasturah.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        val imageView = ImageView(context)
//        // setting height and width of imageview
//        imageView.layoutParams = LinearLayout.LayoutParams(400, 400)
//        imageView.x = 20F //setting margin from left
//        imageView.y = 20F
//        val uri = "@drawable/rekomendasi_borobudur.png"
//        val imageResource = resources.getIdentifier(uri, null, getActivity()?.getPackageName())
//        val res = resources.getDrawable(imageResource)
//        imageView.setImageDrawable(res)
//        val layout = binding.relativeRekomendasi

        // Add ImageView to LinearLayout
//        layout?.addView(imageView)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        LinearLayout myRoot = (LinearLayout)findViewById(binding.linearRekomendasi)
//        val textView: TextView = binding.textHome
//        homeViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
//
//        // Navigate to LoginPage. Well, ignore it. for testing purpose
//        val button: Button = binding.btnDeskripsi
//        button.setOnClickListener{
//            val moveToLogin = Intent(context, DetailActivity::class.java)
//            startActivity(moveToLogin)
//        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}