package com.vkutuev.tosport.chats.messages

import com.vkutuev.tosport.model.User

data class MessageCardData(val sender: User,
                           val text: String)