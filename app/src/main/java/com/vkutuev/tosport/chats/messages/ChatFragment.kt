package com.vkutuev.tosport.chats.messages

import android.app.Fragment
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vkutuev.tosport.R
import com.vkutuev.tosport.Singleton
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch


class ChatFragment : Fragment() {

    lateinit var mMenu: BottomNavigationView

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val layout = inflater!!.inflate(R.layout.chat_layout, container, false)
        val chatId = arguments!!.getInt("chatId")

        val mRecyclerView = layout.findViewById<RecyclerView>(R.id.messages_recycler_view).apply {
            layoutManager = LinearLayoutManager(context)
        }

        launch(UI) {
            val messages = async(CommonPool) {
                ArrayList(Singleton.instance.serverAPI.getChatMessages(chatId))
            }.await()
            mRecyclerView.adapter = MessagesRecyclerViewAdapter(messages)
        }

        mMenu = activity.findViewById(R.id.bottomNavigationView)
        mMenu.visibility = View.GONE

        return layout
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mMenu.visibility = View.VISIBLE
    }
}