package com.vkutuev.tosport.model

import android.graphics.Bitmap
import com.vkutuev.tosport.Singleton

class SportsGround(var id: Int,
                   var coordinates: Pair<Double, Double>,
                   var sport: SportsKinds,
                   var information: String) {

    val admin: User by lazy {
        return@lazy Singleton.instance.serverAPI.getSportsGroundAdmin(id)
    }

    val photo: Bitmap?
        get() = Singleton.instance.serverAPI.getSportsGroundPhoto(id)
    // TODO add members to Sports ground
}