package com.vkutuev.tosport

import android.app.FragmentManager
import com.vkutuev.tosport.model.MockServerAPI
import com.vkutuev.tosport.model.ServerAPI
import com.vkutuev.tosport.model.User

class Singleton private constructor() {
    private object Holder {val INSTANCE = Singleton()}

    companion object {
        val instance: Singleton by lazy { Holder.INSTANCE }
    }

    val serverAPI: ServerAPI = MockServerAPI() // TODO change mock implementation to real

    var activeUser: User? = serverAPI.getUserById(0) // TODO change getting active user

    lateinit var fragmentManager: FragmentManager
}