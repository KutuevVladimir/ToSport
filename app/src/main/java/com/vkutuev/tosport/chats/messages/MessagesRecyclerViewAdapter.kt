package com.vkutuev.tosport.chats.messages

import android.annotation.SuppressLint
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.LinearLayout.LayoutParams
import android.widget.TextView
import com.vkutuev.tosport.R
import com.vkutuev.tosport.Singleton
import com.vkutuev.tosport.model.Message
import com.vkutuev.tosport.model.MapVote
import com.vkutuev.tosport.chats.messages.MessagesRecyclerViewAdapter.ViewHolder

const val CARD_LEFT = 2
const val CARD_RIGHT = 3
const val CARD_TEXT_VOTE = 5
const val CARD_MAP_VOTE = 7

class MessagesRecyclerViewAdapter(private val messages: ArrayList<Message>) : RecyclerView.Adapter<ViewHolder>() {

    private var mVoteRecyclerViewId = View.generateViewId()
    private var mVoteButtonId = View.generateViewId()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = when {
            viewType % CARD_LEFT == 0 -> {
                LayoutInflater.from(parent.context).inflate(R.layout.message_card_left, parent, false)
            }
            else -> {
                LayoutInflater.from(parent.context).inflate(R.layout.message_card_right, parent, false)
            }
        }

        if (viewType % CARD_TEXT_VOTE != 0 && viewType % CARD_MAP_VOTE != 0)
            return ViewHolder(view)

        val linearLayout = view.findViewById<LinearLayout>(R.id.message_content)
        // If necessary Add Map

        // Add RecyclerView
        val rvParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        val recyclerView = RecyclerView(linearLayout.context)
        recyclerView.layoutParams = rvParams
        recyclerView.id = mVoteRecyclerViewId
        linearLayout.addView(recyclerView)

        return ViewHolder(view)
    }

    override fun getItemViewType(position: Int): Int {
        val message = messages[position]
        val currentUserId = Singleton.instance.activeUser!!.id
         var result = if (message.sender.id == currentUserId) CARD_RIGHT else CARD_LEFT
        if (message.vote != null) {
            result *= if (message.vote is MapVote) CARD_MAP_VOTE else CARD_TEXT_VOTE
        }
        return result
    }

    override fun getItemCount(): Int = messages.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val message = messages[position]
        val avatar = message.sender.avatar
        val viewType = getItemViewType(position)

        holder.text?.text = message.text
        if (avatar == null)
            holder.icon?.setImageResource(R.drawable.ic_no_avatar)
        else
            holder.icon?.setImageBitmap(avatar)

        // TODO Add map if it's necessary

        holder.voteVariants?.let {
            it.layoutManager = LinearLayoutManager(it.context)
            it.adapter = VoteRecyclerViewAdapter(message.vote!!)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val icon: ImageView? = itemView.findViewById(R.id.message_card_sender_photo)
        val text: TextView? = itemView.findViewById(R.id.message_card_text )
        // TODO Add map to holder
        val voteVariants: RecyclerView? = itemView.findViewById(mVoteRecyclerViewId)
    }

}