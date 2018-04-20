package com.vkutuev.tosport.model

import android.graphics.Bitmap

interface ServerAPI {
    // TODO доработать API (когда буду пилить сервер)
    // TODO add information about methods
    fun createUser(user: User): Int
    fun updateUser(user: User): Int
    fun getUserById(userId: Int): User?
    fun getUserByUsername(username: String): User?
    fun getUserChatsList(userId: Int): List<Chat>?
    fun getUserFriendsList(userId: Int): List<User>?
    fun getUserAvatar(userId: Int): Bitmap?
    fun getChatMessages(chatId: Int): List<Message>?
    fun getChatMembers(chatId: Int): List<User>?
    fun getSportsGroundsList(): List<SportsGround>
    fun getSportsGroundInformation(sportsGroundId: Int): SportsGround?
    fun getSportsGroundAdmin(sportsGroundId: Int): User
    fun createSportsGround(sportsGround: SportsGround)
}