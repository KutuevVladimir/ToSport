package com.vkutuev.tosport.model

class MockServerAPI: ServerAPI {

    private val users = HashMap<Int, String>()
    private val sportsGround = ArrayList<SportsGround>()


    init {
        users[0] = "VKutuev"
        users[1] = "Ivanov_Ivan"
        users[2] = "Fedor228"
        users[3] = "dmitro"
        users[4] = "Sergey not gay"
        users[5] = "petya"
        users[6] = "qwerty1234"
        users[7] = "Misha1998"
        users[8] = "Runner"
        users[9] = "Conor McGregor"

        sportsGround.add(SportsGround(0, Pair(59.873449, 29.828753), SportsKinds.Football, "PUNK Football field"))
        sportsGround.add(SportsGround(1, Pair(59.873826, 29.826848), SportsKinds.Basketball, "PUNK Basketball sportsground"))
        sportsGround.add(SportsGround(2, Pair(59.873550, 29.826348), SportsKinds.Basketball, "Test0"))
        sportsGround.add(SportsGround(3, Pair(59.873760, 29.826448), SportsKinds.Basketball, "Test1"))
        sportsGround.add(SportsGround(4, Pair(59.873070, 29.826848), SportsKinds.Basketball, "Test2"))
        sportsGround.add(SportsGround(5, Pair(59.873380, 29.826348), SportsKinds.Basketball, "Test3"))
        sportsGround.add(SportsGround(6, Pair(59.873600, 29.826848), SportsKinds.Basketball, "Test4"))
        sportsGround.add(SportsGround(7, Pair(59.874100, 29.826948), SportsKinds.Basketball, "Test5"))

    }

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
                1 -> {
                    val members = ArrayList<User>()
                    members.add(getUserById(0)!!)
                    members.add(getUserById(2)!!)
                    members
                }
                3 -> {
                    val members = ArrayList<User>()
                    members.add(getUserById(0)!!)
                    members.add(getUserById(3)!!)
                    members
                }
                4 -> {
                    val members = ArrayList<User>()
                    members.add(getUserById(0)!!)
                    members.add(getUserById(4)!!)
                    members
                }
                5 -> {
                    val members = ArrayList<User>()
                    members.add(getUserById(0)!!)
                    members.add(getUserById(5)!!)
                    members
                }
                6 -> {
                    val members = ArrayList<User>()
                    members.add(getUserById(0)!!)
                    members.add(getUserById(6)!!)
                    members
                }
                7 -> {
                    val members = ArrayList<User>()
                    members.add(getUserById(0)!!)
                    members.add(getUserById(7)!!)
                    members
                }
                8 -> {
                    val members = ArrayList<User>()
                    members.add(getUserById(0)!!)
                    members.add(getUserById(8)!!)
                    members
                }
                else -> null
            }

    override fun getSportsGroundsList(): List<SportsGround> {
        return sportsGround
    }

    override fun getSportsGroundInformation(sportsGroundId: Int): SportsGround {
        return sportsGround[sportsGroundId]
    }

    override fun createSportsGround(sportsGround: SportsGround) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getUserById(userId: Int): User? {
        users[userId]?.let {
            return User(userId, it)
        }
        return null
    }

    override fun getUserByUsername(username: String): User? {
        users.forEach { if (it.value == username) return User(it.key, it.value)}
        return null
    }

    override fun getUserFriendsList(userId: Int): List<User>? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getUserChatsList(userId: Int): List<Chat>? =
            when(userId) {
                0 -> {
                    val chats = ArrayList<Chat>()
                    chats.add(Chat(0, "Ivanov_Ivan"))
                    chats.add(Chat(1, "Fedor228"))
                    chats.add(Chat(2, "Conor McGregor"))
                    chats.add(Chat(3, "Runner"))
                    chats.add(Chat(4, "Sergey not gay"))
                    chats.add(Chat(5, "petya"))
                    chats.add(Chat(6, "Misha1998"))
                    chats.add(Chat(7, "qwerty1234"))
                    chats.add(Chat(8, "dmitro"))
                    chats
                }
                else -> ArrayList()
            }

    override fun getChatMessages(chatId: Int): List<Message>? =
            when(chatId) {
                0 -> null
                1 -> null
                2 -> null
                3 -> null
                4 -> null
                5 -> null
                6 -> null
                7 -> null
                8 -> null
                else -> null
            }
}