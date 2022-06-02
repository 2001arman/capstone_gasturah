package com.gasturah.ui.location

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.SearchView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.gasturah.MainActivity
import com.gasturah.R
import com.gasturah.databinding.FragmentMapsBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.concurrent.Executors


class MapsFragment : Fragment() {

    private var _binding: FragmentMapsBinding? = null
    private val binding get() = _binding!!

    private val executor = Executors.newSingleThreadExecutor()
    private val handler = Handler(Looper.getMainLooper())

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private val requestLocation = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (!isGranted) {
            Toast.makeText(this.requireContext(), "Please Allow Location", Toast.LENGTH_SHORT).show()
            requestLocation()
        }
    }

    private val callback = OnMapReadyCallback { googleMap ->
        getMyLocation(googleMap)
        setupAction(googleMap)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requestLocation()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this.requireContext())
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    private fun setupAction(mMap: GoogleMap) {

        mMap.uiSettings.isMyLocationButtonEnabled   = false
        mMap.uiSettings.isZoomGesturesEnabled       = true
        mMap.uiSettings.isZoomControlsEnabled       = true

        val bottomSheetLayout       = binding.includeBottomLayout.bottomSheet
        val bottomSheetBehavior     = BottomSheetBehavior.from(bottomSheetLayout)
        val searchArea              = bottomSheetLayout.findViewById<SearchView>(R.id.maps_search_edit)
        val btnCurrentLocation      = bottomSheetLayout.findViewById<FloatingActionButton>(R.id.get_location)

        bottomSheetBehavior.addBottomSheetCallback(object : BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                bottomSheetBehavior.peekHeight = 240
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
               bottomSheetBehavior.peekHeight = 240
                closeKeyboard()
            }

        })

        searchArea.setOnClickListener {
            bottomSheetBehavior.state   = BottomSheetBehavior.STATE_EXPANDED
            searchArea.onActionViewExpanded()
        }

        searchArea.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String): Boolean {
                executor.execute {
                    bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
                    searchLocation(p0, mMap)
                }
                return false
            }
            override fun onQueryTextChange(p0: String): Boolean {
                return false
            }
        })

        btnCurrentLocation.setOnClickListener {
            bottomSheetBehavior.state   = BottomSheetBehavior.STATE_COLLAPSED
            getLastLocation(mMap)
        }
    }

    private fun requestLocation() {
        requestLocation.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    private fun getMyLocation(mMap: GoogleMap) {
        if (ContextCompat.checkSelfPermission(
                this.requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            mMap.isMyLocationEnabled = true
        }
    }

    private fun searchLocation(p0: String, mMap: GoogleMap){

        val location: String = p0
        val addressList: List<Address>?

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
                    mMap.addMarker(MarkerOptions().position(latLng).title(location).snippet(address.getAddressLine(0)))
                    mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng))
                }
            }
        }
    }

    private fun getLastLocation(mMap: GoogleMap) {
        if (ActivityCompat.checkSelfPermission(
                this.requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this.requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestLocation()
            return
        }
        fusedLocationClient.lastLocation.addOnSuccessListener {
            val lastLocationLatLng = LatLng(it.latitude, it.longitude)
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(lastLocationLatLng, 15f))
        }
    }

    fun closeKeyboard() {
        val activity = activity as MainActivity

        val view = activity.currentFocus
        if (view != null) {
            val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}