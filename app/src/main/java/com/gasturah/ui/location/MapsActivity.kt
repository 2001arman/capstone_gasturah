package com.gasturah.ui.location

import android.Manifest
import android.content.pm.PackageManager
import android.location.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.gasturah.R
import com.gasturah.databinding.ActivityMapsBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.util.concurrent.Executors

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private val executor = Executors.newSingleThreadExecutor()
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        setupView()
        setupAction()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap;
        getMyLocation()
    }

    private fun setupView() {
        supportActionBar?.hide()
    }

    private fun setupAction() {
        binding.mapsSearchEdit.setOnClickListener {
            binding.mapsSearchEdit.onActionViewExpanded()
        }
        binding.mapsSearchEdit.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String): Boolean {
                executor.execute {
                    searchLocation(p0)
                }
                return false
            }

            override fun onQueryTextChange(p0: String): Boolean {
                return false
            }

        })
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                getMyLocation()
            }
        }

    private fun getMyLocation() {
        if (ContextCompat.checkSelfPermission(
                this.applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            mMap.isMyLocationEnabled = true
        } else {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    private fun searchLocation(p0: String){

        val location: String = p0
        var addressList: List<Address>? = null

        if (location.isEmpty()){
            Toast.makeText(this, "provide location", Toast.LENGTH_SHORT).show()
        }else{
            val geoCoder    = Geocoder(this)
            addressList     = geoCoder.getFromLocationName(location, 1)

            handler.post {
                if(addressList.isEmpty()) {
                    Toast.makeText(this, "Not found", Toast.LENGTH_SHORT).show()
                } else {
                    val address = addressList[0]
                    val latLng  = LatLng(address.latitude, address.longitude)
                    mMap.addMarker(MarkerOptions().position(latLng).title(location))
                    mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng))
                }
            }
        }
    }
}