package com.vkutuev.tosport.chats.messages

import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.vkutuev.tosport.R
import com.vkutuev.tosport.Singleton
import com.vkutuev.tosport.model.Message

class MessagesRecyclerViewAdapter(private val messages: ArrayList<Message>) : RecyclerView.Adapter<MessagesRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = when(viewType) {
            0 -> LayoutInflater.from(parent.context).inflate(R.layout.message_card_left, parent, false)
            else -> LayoutInflater.from(parent.context).inflate(R.layout.message_card_right, parent, false)
        }
        return ViewHolder(view)
    }

    override fun getItemViewType(position: Int): Int = if (messages[position].sender.id == Singleton.instance.activeUser!!.id) 0 else 1


    override fun getItemCount(): Int = messages.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val message = messages[position]
        holder.text.text = message.text
        val avatar = message.sender.avatar
        if (avatar != null) {
            holder.icon.setImageBitmap(avatar)
        }
        else {
            holder.icon.setImageResource(R.drawable.ic_no_avatar)
            // TODO if there is not avatar photo then set a ic_no_avatar
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val isLeftSide = itemView.findViewById<CardView>(R.id.message_card_left_canvas) != null
        val icon: ImageView = if (isLeftSide) itemView.findViewById(R.id.message_card_left_sender_photo) else itemView.findViewById(R.id.message_card_right_sender_photo)
        val text: TextView = if (isLeftSide) itemView.findViewById(R.id.message_card_left_text) else itemView.findViewById(R.id.message_card_right_text )
    }
}