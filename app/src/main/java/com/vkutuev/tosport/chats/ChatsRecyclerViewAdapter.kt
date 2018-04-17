package com.vkutuev.tosport.chats

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.vkutuev.tosport.R

class ChatsRecyclerViewAdapter(cards: ArrayList<ChatCardData>) : RecyclerView.Adapter<ChatsRecyclerViewAdapter.ViewHolder>() {

    private val chatCards = cards

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.chat_card, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = chatCards.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val card = chatCards[position]
        holder.chatTitle.text = card.title
        holder.chatMessage.text = card.message
        if (card.photo != null) {
            holder.chatIcon.setImageBitmap(card.photo)
        }
        else {
            holder.chatIcon.setImageResource(R.drawable.ic_no_avatar)
//            holder.chatIcon.setImageDrawable()
            // TODO if there is not avatar photo then set a ic_no_avatar
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val chatTitle: TextView = itemView.findViewById(R.id.chat_card_title)
        val chatMessage: TextView = itemView.findViewById(R.id.chat_card_message)
        val chatIcon: ImageView = itemView.findViewById(R.id.chat_card_photo)
        val divider: View = itemView.findViewById(R.id.chat_card_divider)
    }
}