package com.vkutuev.tosport.model

import android.graphics.Bitmap
import com.vkutuev.tosport.Singleton

class User(val id: Int,
           val username: String) {
    val chatsList: ArrayList<Chat> by lazy {
        val result = Singleton.instance.serverAPI.getUserChatsList(id) ?: return@lazy ArrayList<Chat>()
        return@lazy ArrayList(result)
    }

    // TODO change friends getting politic if it will be necessary
    val friendsList: ArrayList<User> by lazy {
        val result = Singleton.instance.serverAPI.getUserFriendsList(id) ?:return@lazy ArrayList<User>()
        return@lazy ArrayList(result)
    }

    val avatar: Bitmap? by lazy {
        return@lazy Singleton.instance.serverAPI.getUserAvatar(id)
    }
}