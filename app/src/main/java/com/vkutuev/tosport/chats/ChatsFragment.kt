package com.vkutuev.tosport.chats

import android.app.Activity
import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import com.vkutuev.tosport.R

class ChatsFragment : Fragment() {

    var names = arrayOf("Иван", "Марья", "Петр", "Антон", "Даша", "Борис", "Костя", "Игорь", "Анна", "Денис",
            "Андрей", "Петр", "Антон", "Даша", "Борис", "Костя", "Игорь", "Анна", "Денис", "Андрей")
    lateinit var mActivity: Activity

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val chatsFV = inflater?.inflate(R.layout.chats_layout, container, false)!!
        mActivity = activity!!
        // TODO Нормально обработать, если null
        val chatsLV = chatsFV.findViewById<ListView>(R.id.dialogs_list)
        val adapter = ArrayAdapter<String>( mActivity, android.R.layout.simple_list_item_1, names)
        chatsLV.adapter = adapter
        return chatsFV
    }
}