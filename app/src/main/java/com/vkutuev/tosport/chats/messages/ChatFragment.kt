package com.vkutuev.tosport.chats.messages

import android.app.Fragment
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vkutuev.tosport.R
import com.vkutuev.tosport.Singleton
import com.vkutuev.tosport.chats.ChatsRecyclerViewAdapter

class ChatFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val layout = inflater!!.inflate(R.layout.chat_layout, container, false)
        val chatId = arguments!!.getInt("chatId")

        val recyclerView = layout.findViewById<RecyclerView>(R.id.messages_recycler_view).apply {
            adapter = MessagesRecyclerViewAdapter(ArrayList(Singleton.instance.serverAPI.getChatMessages(chatId)))
            layoutManager = LinearLayoutManager(context)
        }


        return layout
    }
}