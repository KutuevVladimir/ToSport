package com.vkutuev.tosport.model

import com.vkutuev.tosport.Singleton

class User(val id: Int,
           val username: String) {
    val chatsList: ArrayList<Chat> by lazy {
        val api = Singleton.instance.serverAPI
        return@lazy ArrayList(api.getUserChatsList(id))
    }

    val friendsList: ArrayList<User> by lazy {
        val api = Singleton.instance.serverAPI
        return@lazy ArrayList(api.getUserFriendsList(id))
    }
}