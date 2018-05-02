package com.vkutuev.tosport.chats

import android.os.Bundle
import android.provider.Settings.Global.getString
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.vkutuev.tosport.R
import com.vkutuev.tosport.Singleton
import com.vkutuev.tosport.chats.messages.ChatFragment
import com.vkutuev.tosport.model.Chat

class ChatsRecyclerViewAdapter(private val chats: ArrayList<Chat>) : RecyclerView.Adapter<ChatsRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.chat_card, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = chats.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val chat = chats[position]
        holder.chatTitle.text = chat.title

        holder.chatMessage.text = if (chat.messages.isEmpty()) "There are not messages in chat" else chat.messages[0].text
        if (chat.photo != null) {
            holder.chatIcon.setImageBitmap(chat.photo)
        }
        else {
            holder.chatIcon.setImageResource(R.drawable.ic_no_avatar)
//            holder.chatIcon.setImageDrawable()
            // TODO if there is not avatar photo then set a ic_no_avatar
        }

        holder.chatTitle.setOnClickListener {
            openChat(chat)
        }

        holder.chatMessage.setOnClickListener {
            openChat(chat)
        }

        holder.chatCard.setOnClickListener {
            openChat(chat)
        }
    }

    private fun openChat(chat: Chat) {
        val chatFragment = ChatFragment()
        val bundle = Bundle()
        bundle.putInt("chatId", chat.id)
        chatFragment.arguments = bundle
        Singleton.instance.fragmentManager.beginTransaction()
                .replace(R.id.content_frame, chatFragment)
                .addToBackStack(chatFragment.toString())
                .commit()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val chatTitle: TextView = itemView.findViewById(R.id.chat_card_title)
        val chatMessage: TextView = itemView.findViewById(R.id.chat_card_message)
        val chatIcon: ImageView = itemView.findViewById(R.id.chat_card_photo)
        val divider: View = itemView.findViewById(R.id.chat_card_divider)
        val chatCard = itemView
    }
}