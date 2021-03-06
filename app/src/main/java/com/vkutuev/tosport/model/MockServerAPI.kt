package com.vkutuev.tosport.model

import android.graphics.Bitmap

class MockServerAPI: ServerAPI {

    private val users = HashMap<Int, String>()
    private val sportsGround = ArrayList<SportsGround>()
    private val votes = ArrayList<Vote>()
    private val sportsGroundPhotos = HashMap<Int, Bitmap>()

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
        sportsGround.add(SportsGround(2, Pair(59.849035, 30.143025), SportsKinds.Basketball, "Test0"))
        sportsGround.add(SportsGround(3, Pair(59.873760, 29.826448), SportsKinds.Basketball, "Test1"))
        sportsGround.add(SportsGround(4, Pair(59.873070, 29.826848), SportsKinds.Basketball, "Test2"))
        sportsGround.add(SportsGround(5, Pair(59.873380, 29.826348), SportsKinds.Basketball, "Test3"))
        sportsGround.add(SportsGround(6, Pair(59.873600, 29.826848), SportsKinds.Basketball, "Test4"))
        sportsGround.add(SportsGround(7, Pair(59.874100, 29.826948), SportsKinds.Basketball, "Test5"))

        var variants = ArrayList<Pair<String, Int>>()
        variants.add(Pair("16:00", 2))
        variants.add(Pair("17:30", 1))
        var responding = ArrayList<Int>()
        responding.add(0)
        responding.add(1)
        votes.add(Vote(variants, responding))

        variants = ArrayList()
        variants.add(Pair("18:00", 0))
        variants.add(Pair("18:30", 0))
        responding = ArrayList()
        votes.add(Vote(variants, responding))

        variants = ArrayList()
        variants.add(Pair("1", 1))
        variants.add(Pair("2", 0))
        responding = ArrayList()
        responding.add(1)
        var points = ArrayList<Pair<Double, Double>>()
        points.add(sportsGround[0].coordinates)
        points.add(sportsGround[2].coordinates)
        votes.add(MapVote(variants, responding, points))

    }

    override fun createUser(user: User): Int {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun updateUser(user: User): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getUserAvatar(userId: Int): Bitmap? {
        return null
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

    override fun getSportsGroundAdmin(sportsGroundId: Int): User =
        when (sportsGroundId) {
            0, 1 -> getUserById(0)!!
            else -> getUserById(1)!!
        }

    override fun getSportsGroundPhoto(sportsGroundId: Int): Bitmap? =
            sportsGroundPhotos[sportsGroundId]

    override fun setSportsGroundPhoto(sportsGroundId: Int, bitmap: Bitmap) {
        sportsGroundPhotos[sportsGroundId] = bitmap
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
                0 -> {
                    val messages = ArrayList<Message>()
                    messages.add(Message("Hello", null, getUserById(0)!!))
                    messages.add(Message("Where?", votes[2], getUserById(1)!!))
                    messages.add(Message("Hi", null, getUserById(1)!!))
                    messages.add(Message("W", null, getUserById(0)!!))
                    messages.add(Message("H", null, getUserById(0)!!))
                    messages.add(Message("A", null, getUserById(0)!!))
                    messages.add(Message("T", null, getUserById(0)!!))
                    messages.add(Message("S", null, getUserById(0)!!))
                    messages.add(Message("U", null, getUserById(0)!!))
                    messages.add(Message("P", null, getUserById(0)!!))
                    messages.add(Message("S", null, getUserById(0)!!))
                    messages.add(Message("?", null, getUserById(0)!!))
                    messages.add(Message("Great!", null, getUserById(1)!!))
                    messages.add(Message("What time you prefer?", votes[0], getUserById(0)!!))
                    messages.add(Message("Maybe like this?", votes[1], getUserById(1)!!))
                    messages
                }
                1,2,3,4,5,6,7,8 -> ArrayList()
                else -> null
            }

    override fun getChatPhoto(chatId: Int): Bitmap? {
        return null
    }
}