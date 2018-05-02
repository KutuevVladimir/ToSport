package com.vkutuev.tosport.chats.messages

import android.content.res.ColorStateList
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.vkutuev.tosport.R
import com.vkutuev.tosport.Singleton
import com.vkutuev.tosport.model.Message

const val CARD_LEFT = 0
const val CARD_RIGHT = 1

class MessagesRecyclerViewAdapter(private val messages: ArrayList<Message>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
            when(viewType) {
                CARD_LEFT -> {
                    val view = LayoutInflater.from(parent.context).inflate(R.layout.message_card_left, parent, false)
                    ViewHolderLeft(view)
                }
                else -> {
                    val view = LayoutInflater.from(parent.context).inflate(R.layout.message_card_right, parent, false)
                    ViewHolderRight(view)
                }
            }

    override fun getItemViewType(position: Int): Int =
            if (messages[position].sender.id != Singleton.instance.activeUser!!.id) CARD_LEFT else CARD_RIGHT


    override fun getItemCount(): Int = messages.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = messages[position]
        val avatar = message.sender.avatar

        when (getItemViewType(position)) {
            CARD_LEFT -> {
                holder as ViewHolderLeft
                holder.textLeft?.text = message.text
//                holder.textLeft?.textSize = 14.0f
//                holder.textLeft?.text
                if (avatar == null)
                    holder.iconLeft?.setImageResource(R.drawable.ic_no_avatar)
                else
                    holder.iconLeft?.setImageBitmap(avatar)
            }
            else -> {
                holder as ViewHolderRight
                holder.textRight?.text = message.text
//                holder.textRight?.textSize = 14.0f
                if (avatar == null)
                    holder.iconRight?.setImageResource(R.drawable.ic_no_avatar)
                else
                    holder.iconRight?.setImageBitmap(avatar)
            }
        }
    }

    class ViewHolderLeft(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val iconLeft: ImageView? = itemView.findViewById(R.id.message_card_left_sender_photo)
        val textLeft: TextView? = itemView.findViewById(R.id.message_card_left_text )
//        val cardLeft: CardView? = itemView.findViewById(R.id.message_card_left_canvas)
    }

    class ViewHolderRight(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val iconRight: ImageView? = itemView.findViewById(R.id.message_card_right_sender_photo)
        val textRight: TextView? = itemView.findViewById(R.id.message_card_right_text )
//        val cardRight: CardView? = itemView.findViewById(R.id.message_card_right_canvas)
    }
}