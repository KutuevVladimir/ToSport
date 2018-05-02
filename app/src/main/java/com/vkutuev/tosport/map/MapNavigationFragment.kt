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
import com.google.maps.android.clustering.ClusterManager
import com.vkutuev.tosport.R
import com.vkutuev.tosport.Singleton
import com.vkutuev.tosport.map.sportsground.SportsGroundFragment
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch

const val PERMISSION_LOCATION = 1

class MapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var mClusterManager: ClusterManager<AbstractMarker>

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
        }
        else {
            requestPermissions(arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), PERMISSION_LOCATION)
        }
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