package com.vkutuev.tosport.chats.messages

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.ProgressBar
import android.widget.TextView
import com.vkutuev.tosport.R
import com.vkutuev.tosport.Singleton
import com.vkutuev.tosport.model.vote.Vote

const val BUTTON = 0
const val VOTE_VARIANT = 1

class VoteRecyclerViewAdapter(private val vote: Vote) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val mMaxVotesCount: Int
        get() {
            var temp = vote.variants[0].second
            vote.variants.forEach {
                if (it.second > temp)
                    temp = it.second
            }
            return temp
        }

    private val checkedVariants = HashSet<Int>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
            when (viewType) {
                VOTE_VARIANT -> {
                    val view = LayoutInflater.from(parent.context).inflate(R.layout.vote_layout, parent, false)
                    VoteElementViewHolder(view)
                }
                else -> ButtonViewHolder(Button(parent.context))
            }


    override fun getItemViewType(position: Int): Int =
        when (position) {
            vote.variants.size -> BUTTON
            else -> VOTE_VARIANT
        }

    override fun getItemCount() =
        if (vote.respondingIds.contains(Singleton.instance.activeUser!!.id))
            vote.variants.size
        else
            vote.variants.size + 1


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            VOTE_VARIANT -> {
                holder as VoteElementViewHolder
                holder.text?.text = "${position + 1}) ${vote.variants[position].first}"
                holder.progressBar?.apply {
                    max = mMaxVotesCount
                    progress = vote.variants[position].second
                }

                if (vote.respondingIds.contains(Singleton.instance.activeUser!!.id))
                    holder.checkBox?.visibility = View.GONE
                else
                    holder.checkBox?.setOnCheckedChangeListener { buttonView, isChecked ->
                        if (isChecked)
                            checkedVariants.add(position)
                        else
                            checkedVariants.remove(position)
                    }
            }
            else -> {
                holder as ButtonViewHolder
                holder.button.text = "Vote"
                holder.button.setOnClickListener(voteButtonOnClickListener)
            }
        }
    }

    private val voteButtonOnClickListener = View.OnClickListener {
        vote.respondingIds.add(Singleton.instance.activeUser!!.id)
        checkedVariants.forEach {
            vote.variants[it] = Pair(vote.variants[it].first, vote.variants[it].second + 1)
        }
        notifyDataSetChanged()

    }

    class VoteElementViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val text: TextView? = itemView.findViewById(R.id.vote_variant_text)
        val progressBar: ProgressBar? = itemView.findViewById(R.id.vote_count)
        val checkBox: CheckBox? = itemView.findViewById(R.id.vote_check_box)
    }

    class ButtonViewHolder(val button: Button) : RecyclerView.ViewHolder(button)
}