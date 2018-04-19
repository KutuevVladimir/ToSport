package com.vkutuev.tosport.model

class MockServerAPI: ServerAPI {
    override fun createUser(user: User): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun updateUser(user: User): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getChatMembers(chatId: Int): List<User>? =
            when(chatId) {
                0 -> {
                    val members = ArrayList<User>()
                    members.add(getUserById(0)!!)
                    members.add(getUserById(1)!!)
                    members
                }
                else -> null
            }

    override fun getSportsGroundsList(): List<SportsGround> {
        val sportsGround = ArrayList<SportsGround>()
        sportsGround.add(SportsGround(Pair(59.873449, 29.828753), SportsKinds.Football, "PUNK Football field"))
        return sportsGround
    }

    override fun getSportsGroundInformation(sportsGroundId: Int): SportsGround {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun createSportsGround(sportsGround: SportsGround) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getUserById(userId: Int): User? =
            when(userId) {
                0 -> User(userId, "Vladimir Kutuev")
                1 -> User(userId, "Ivanov Ivan")
                else -> null
            }

    override fun getUserByUsername(username: String): User? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getUserFriendsList(userId: Int): List<User>? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getUserChatsList(userId: Int): List<Chat>? =
            when(userId) {
                0 -> {
                    val chats = ArrayList<Chat>()
                    chats.add(Chat(0, "Ivanov Vanek"))
                    chats
                }
                else -> ArrayList()
            }

    override fun getChatMessages(chatId: Int): List<Message>? =
            when(chatId) {
                0 -> null
                else -> null
            }
}