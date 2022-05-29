package com.gasturah.ui.location

import android.Manifest
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.gasturah.R
import com.gasturah.databinding.FragmentHomeBinding
import com.gasturah.databinding.FragmentMapsBinding

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.util.concurrent.Executors

class MapsFragment : Fragment() {
    private val executor = Executors.newSingleThreadExecutor()
    private val handler = Handler(Looper.getMainLooper())

    private var _binding: FragmentMapsBinding? = null
    private val binding get() = _binding!!

    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        getMyLocation(googleMap)
        setupAction(googleMap)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMapsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    private fun setupAction(mMap: GoogleMap) {
        binding.mapsSearchEdit.setOnClickListener {
            binding.mapsSearchEdit.onActionViewExpanded()
        }
        binding.mapsSearchEdit.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String): Boolean {
                executor.execute {
                    searchLocation(p0, mMap)
                }
                return false
            }

            override fun onQueryTextChange(p0: String): Boolean {
                return false
            }
        })
//        binding.btnCurrentLocation.setOnClickListener() {
//            if (ActivityCompat.checkSelfPermission(
//                    this,
//                    Manifest.permission.ACCESS_FINE_LOCATION
//                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
//                    this,
//                    Manifest.permission.ACCESS_COARSE_LOCATION
//                ) != PackageManager.PERMISSION_GRANTED
//            ) {
//                // TODO: Consider calling
//                //    ActivityCompat#requestPermissions
//                // here to request the missing permissions, and then overriding
//                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                //                                          int[] grantResults)
//                // to handle the case where the user grants the permission. See the documentation
//                // for ActivityCompat#requestPermissions for more details.
//                return@setOnClickListener
//            }
//            mFusedLocationClient.lastLocation.addOnCompleteListener(this) { task ->
//                val location: Location? = task.result
//                val currentLocation = LatLng(location!!.latitude, location!!.longitude)
//                mMap.clear()
//                mMap.addMarker(MarkerOptions().position(currentLocation))
//                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 16F))
//            }
//        }
    }

    private fun getMyLocation(mMap: GoogleMap) {
        if (ContextCompat.checkSelfPermission(
                this.requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            mMap.isMyLocationEnabled = true
        } else {
            registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted: Boolean ->
                if (isGranted) {
                    getMyLocation(mMap)
                }
            }
        }
    }

    private fun searchLocation(p0: String, mMap: GoogleMap){

        val location: String = p0
        var addressList: List<Address>? = null

        if (location.isEmpty()){
            Toast.makeText(this.requireContext(), "provide location", Toast.LENGTH_SHORT).show()
        }else{
            val geoCoder    = Geocoder(this.requireContext())
            addressList     = geoCoder.getFromLocationName(location, 1)

            handler.post {
                if(addressList.isEmpty()) {
                    Toast.makeText(this.requireContext(), "Not found", Toast.LENGTH_SHORT).show()
                } else {
                    val address = addressList[0]
                    val latLng  = LatLng(address.latitude, address.longitude)
                    mMap.addMarker(MarkerOptions().position(latLng).title(location))
                    mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng))
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}