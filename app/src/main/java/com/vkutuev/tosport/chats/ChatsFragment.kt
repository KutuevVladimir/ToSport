package com.vkutuev.tosport.chats

import android.app.Fragment
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vkutuev.tosport.R

class ChatsFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private var chatCards = ArrayList<ChatCardData>()

    init {
        chatCards.add(ChatCardData("Test1", "Test message", null))
        chatCards.add(ChatCardData("Test2", "Test message", null))
        chatCards.add(ChatCardData("Test3", "Test message", null))
        chatCards.add(ChatCardData("Test4", "Test message", null))
        chatCards.add(ChatCardData("Test5", "Test message", null))
        chatCards.add(ChatCardData("Test6", "Test message", null))
        chatCards.add(ChatCardData("Test7", "Test message", null))
        chatCards.add(ChatCardData("Test8", "Test message", null))
        chatCards.add(ChatCardData("Test9", "Test message", null))
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val chatsLayout = inflater!!.inflate(R.layout.chats_layout, container, false)
        // TODO Нормально обработать, если null

        recyclerView = chatsLayout.findViewById<RecyclerView>(R.id.recycler_view_chats).apply {
            adapter = ChatsRecyclerViewAdapter(chatCards)
            layoutManager = LinearLayoutManager(context)
        }

        return chatsLayout
    }
}