package com.vkutuev.tosport.map

import android.annotation.SuppressLint
import android.app.Fragment
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.*
import com.google.android.gms.maps.*
import com.google.maps.android.clustering.ClusterManager
import com.vkutuev.tosport.R
import com.vkutuev.tosport.Singleton
import com.vkutuev.tosport.map.sportsground.AddSportGroundFragment
import com.vkutuev.tosport.map.sportsground.SportsGroundFragment
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.MarkerOptions
import com.vkutuev.tosport.PermissionCodes

class MapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var mClusterManager: ClusterManager<AbstractMarker>
    private lateinit var fusedLocationClient: FusedLocationProviderClient


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

        setHasOptionsMenu(true)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)
        return mapFragmentView
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mClusterManager = ClusterManager(context, mMap)

        mMap.setOnCameraMoveListener {
            mClusterManager.cluster()
        }
        mClusterManager.setOnClusterItemClickListener(mOnClusterItemClickListener)
        mMap.setOnMarkerClickListener(mClusterManager)

        launch(UI) {
            val sportsGrounds = async(CommonPool) {
                Singleton.instance.serverAPI.getSportsGroundsList()
            }.await()

            sportsGrounds.forEach {
                mClusterManager.addItem(CustomMarker(it.id, it.sport.toString(), it.information, it.coordinates))
            }
        }

        if (ContextCompat.checkSelfPermission(activity, android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.isMyLocationEnabled = true
            fusedLocationClient.lastLocation.addOnSuccessListener {location ->
                with(location) {
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(latitude, longitude), 15f))
                }
            }
        }
        else {
            requestPermissions(arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), PermissionCodes.Location.permissionCode)
        }
        mClusterManager.cluster()
    }

    private val mOnClusterItemClickListener = ClusterManager.OnClusterItemClickListener<AbstractMarker> {

        val sg = Singleton.instance.serverAPI.getSportsGroundInformation(it.sportsGroundId)
        val sgFragment = SportsGroundFragment()
        val bundle = Bundle()
        bundle.putInt("id", it.sportsGroundId)
        sgFragment.arguments = bundle
        Singleton.instance.fragmentManager.beginTransaction()
                .replace(R.id.content_frame, sgFragment)
                .addToBackStack(sgFragment.toString())
                .commit()

        true
    }

    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            PermissionCodes.Location.permissionCode -> {
                if (grantResults.isNotEmpty() && permissions[0] == android.Manifest.permission.ACCESS_FINE_LOCATION) {
                    mMap.isMyLocationEnabled = true
                    fusedLocationClient.lastLocation.addOnSuccessListener {location ->
                        with(location) {
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(LatLng(latitude, longitude)))
                        }
                    }
                }
                else {
                    // Permission was denied. Display an error message.
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private val mOnMenuItemClickListener = MenuItem.OnMenuItemClickListener {
        val sgFragment = AddSportGroundFragment()
        Singleton.instance.fragmentManager.beginTransaction()
                .replace(R.id.content_frame, sgFragment)
                .addToBackStack(sgFragment.toString())
                .commit()
        true
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        menu?.add(0, View.generateViewId(), 0, R.string.add)
                ?.setOnMenuItemClickListener(mOnMenuItemClickListener)
                ?.setIcon(android.R.drawable.ic_input_add)
                ?.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS or MenuItem.SHOW_AS_ACTION_WITH_TEXT)

        super.onCreateOptionsMenu(menu, inflater)
    }
}