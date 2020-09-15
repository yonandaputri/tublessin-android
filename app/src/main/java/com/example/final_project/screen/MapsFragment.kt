package com.example.final_project.screen

import android.Manifest
import android.content.ContextWrapper
import android.content.pm.PackageManager
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.final_project.R
import com.example.final_project.domain.user.UserLocation
import com.example.final_project.domain.user.UserViewModel

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.pixplicity.easyprefs.library.Prefs
import kotlinx.android.synthetic.main.fragment_maps.*
import java.util.*

class MapsFragment : Fragment(), View.OnClickListener {

    private lateinit var map: GoogleMap
    private val userViewModel = UserViewModel()
    private val REQUEST_LOCATION_PERMISSION = 1
    private lateinit var userId: String

    lateinit var navController: NavController

    private val callback = OnMapReadyCallback { googleMap ->
        map = googleMap
        enableMyLocation()
        userViewModel.requestGetUserDetail(userId)
        userViewModel.getUserAccountInfo().observe(viewLifecycleOwner, Observer {
            var lat = it.result.profile.location.latitude
            var long = it.result.profile.location.longitude
            var userPosition = LatLng(lat, long)
            googleMap.addMarker(
                MarkerOptions().position(userPosition).title("Marker in User Position")
            )
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(userPosition))
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
        navController = Navigation.findNavController(view)
        findNearbyButton.setOnClickListener(this)
        Prefs.Builder()
            .setContext(this.activity)
            .setMode(ContextWrapper.MODE_PRIVATE)
            .setPrefsName(this.activity?.packageName)
            .setUseDefaultSharedPreference(true)
            .build()

        userId = Prefs.getString("id", "0")
        findNearbyButton?.isEnabled = !Prefs.getBoolean("statusOrderan",false)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.contains(PackageManager.PERMISSION_GRANTED)) {
                enableMyLocation()
            }
        }
    }

    private fun isPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            this.requireActivity(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun enableMyLocation() {
        if (isPermissionGranted()) {
            if (ActivityCompat.checkSelfPermission(
                    this.requireActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this.requireActivity(),
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            map.isMyLocationEnabled = true

        } else {
            ActivityCompat.requestPermissions(
                this.requireActivity(),
                arrayOf<String>(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_LOCATION_PERMISSION
            )
        }
    }

    override fun onClick(v: View?) {
        when (v) {
            findNearbyButton -> {
                navController.navigate(R.id.action_mapsFragment_to_nearbyMontirFragment)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val handler = Handler()
        val runnable: Runnable = object : Runnable {
            override fun run() {
                map.clear()
                val userPosition = LatLng(map.myLocation.latitude, map.myLocation.longitude)
                map.addMarker(
                    MarkerOptions().position(userPosition).title("Marker in User Position")
                )
                map.moveCamera(CameraUpdateFactory.newLatLng(userPosition))
                userViewModel.updateUserLocation(
                    userId,
                    UserLocation(map.myLocation.latitude, map.myLocation.longitude)
                )
                Prefs.putDouble("userLocationLatitude", map.myLocation.latitude)
                Prefs.putDouble("userLocationLongitude", map.myLocation.longitude)

                handler.postDelayed(this, 5000)
            }
        }
        handler.postDelayed(runnable, 7000)
    }
}