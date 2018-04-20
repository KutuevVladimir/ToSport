package com.vkutuev.tosport.map

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.ClusterItem

abstract class AbstractMarker(val sportsGroundId: Int,
                              protected var latitude: Double,
                              protected var longitude: Double) : ClusterItem {

    protected lateinit var marker: MarkerOptions

    override fun getPosition(): LatLng = LatLng(latitude, longitude)
}