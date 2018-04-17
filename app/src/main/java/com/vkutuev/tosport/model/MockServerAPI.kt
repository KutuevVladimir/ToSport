package com.vkutuev.tosport.model

class MockServerAPI: ServerAPI {
    override fun createUser(user: User): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun updateUser(user: User): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getChatMembers(chatId: Int): List<User>? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getSportsGroundsList(): List<SportsGround> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getSportsGroundInformation(sportsGroundId: Int): SportsGround {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun createSportsGround(sportsGround: SportsGround) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getUserById(userId: Int): User? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getUserByUsername(username: String): User? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getUserFriendsList(userId: Int): List<User>? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getUserChatsList(userId: Int): List<Chat> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getChatMessages(chatId: Int): List<Message> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}