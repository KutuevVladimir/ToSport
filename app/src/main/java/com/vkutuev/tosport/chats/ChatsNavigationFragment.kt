package com.vkutuev.tosport.chats

import android.app.Fragment
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vkutuev.tosport.R
import com.vkutuev.tosport.Singleton

class ChatsNavigationFragment : Fragment() {

//    private lateinit var recyclerView: RecyclerView
    private var chatCards: ArrayList<ChatCardData>

    init {
        val api = Singleton.instance.serverAPI
        val chats = api.getUserChatsList(0)
        chatCards = ArrayList()
        chats?.forEach {
            chatCards.add(ChatCardData(it.title, if (it.messages.isNotEmpty()) it.messages[0].text else "There are not messages in this chat", null))
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val chatsLayout = inflater!!.inflate(R.layout.chats_layout, container, false)
        // TODO Нормально обработать, если null

        val recyclerView = chatsLayout.findViewById<RecyclerView>(R.id.recycler_view_chats).apply {
            adapter = ChatsRecyclerViewAdapter(chatCards)
            layoutManager = LinearLayoutManager(context)
        }

        return chatsLayout
    }
}