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
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch

class ChatsNavigationFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val chatsLayout = inflater!!.inflate(R.layout.chats_layout, container, false)
        // TODO Нормально обработать, если null

        val recyclerView = chatsLayout.findViewById<RecyclerView>(R.id.recycler_view_chats).apply {
            layoutManager = LinearLayoutManager(context)
        }

        launch(UI) {
            val chatsList = async(CommonPool) {
                Singleton.instance.activeUser!!.chatsList
            }.await()
            recyclerView.adapter = ChatsRecyclerViewAdapter(chatsList)
        }

        return chatsLayout
    }
}