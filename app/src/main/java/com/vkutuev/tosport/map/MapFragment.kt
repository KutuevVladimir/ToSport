package com.vkutuev.tosport.map

import android.annotation.SuppressLint
import android.app.Fragment
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.*
import com.vkutuev.tosport.R

const val PERMISSION_LOCATION = 1

class MapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val mapFragmentView = inflater?.inflate(R.layout.map_layout, container, false)!!
        // TODO Нормально обработать, если null
        val mapView = mapFragmentView.findViewById<MapView>(R.id.map)

        mapView.onCreate(savedInstanceState)
        mapView.onResume()

        try {
            MapsInitializer.initialize(activity.applicationContext)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        mapView.getMapAsync(this)
        return mapFragmentView
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        if (ContextCompat.checkSelfPermission(activity, android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.isMyLocationEnabled = true
        }
        else {
            requestPermissions(arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), PERMISSION_LOCATION)
        }
    }

    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSION_LOCATION -> {
                if (grantResults.isEmpty() && permissions[0] == android.Manifest.permission.ACCESS_FINE_LOCATION) {
                    mMap.isMyLocationEnabled = true
                }
                else {
                    // Permission was denied. Display an error message.
                }
            }
        }
    }
}