package com.vkutuev.tosport.model

import com.vkutuev.tosport.Singleton

class Chat(val id: Int,
           var title: String) {
    val messages: ArrayList<Message> by lazy {
        val result = Singleton.instance.serverAPI.getChatMessages(id) ?: return@lazy ArrayList<Message>()
        return@lazy ArrayList(result)
    }

    val members: List<User> by lazy {
        val result = Singleton.instance.serverAPI.getChatMembers(id)
        return@lazy ArrayList(result)
    }
}