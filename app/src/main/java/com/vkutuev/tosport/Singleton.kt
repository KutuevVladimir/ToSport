package com.vkutuev.tosport

import com.vkutuev.tosport.model.MockServerAPI
import com.vkutuev.tosport.model.ServerAPI

class Singleton private constructor() {
    private object Holder {val INSTANCE = Singleton()}

    companion object {
        val instance: Singleton by lazy { Holder.INSTANCE }
    }

    val serverAPI: ServerAPI = MockServerAPI() // TODO change mock implementation to real
}