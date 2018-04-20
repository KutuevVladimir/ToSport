package com.vkutuev.tosport.map

class CustomMarker(sportsGroundId: Int,
                   private val title: String,
                   private val info: String,
                   coordinates: Pair<Double, Double>) : AbstractMarker(sportsGroundId, coordinates.first, coordinates.second) {
    override fun getSnippet(): String = info // TODO change to constant

    override fun getTitle(): String = title


}