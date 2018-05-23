package com.vkutuev.tosport.chats.messages

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.vkutuev.tosport.R
import com.vkutuev.tosport.Singleton
import com.vkutuev.tosport.chats.messages.MessagesRecyclerViewAdapter.ViewHolder
import com.vkutuev.tosport.model.MapVote
import com.vkutuev.tosport.model.Message
import com.vkutuev.tosport.model.Vote
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch

const val CARD_LEFT = 2
const val CARD_RIGHT = 3
const val CARD_TEXT_VOTE = 5
const val CARD_MAP_VOTE = 7
const val CARD_VOTED = 11

class MessagesRecyclerViewAdapter(private val messages: ArrayList<Message>) : RecyclerView.Adapter<ViewHolder>(){

    private var mMapId = View.generateViewId()
    private lateinit var mGoogleMap: GoogleMap
    private var mNoAvatarImage: Drawable? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = when {
            viewType % CARD_LEFT == 0 -> {
                LayoutInflater.from(parent.context).inflate(R.layout.message_card_left, parent, false)
            }
            else -> {
                LayoutInflater.from(parent.context).inflate(R.layout.message_card_right, parent, false)
            }
        }

        return ViewHolder(view)
    }

    override fun getItemViewType(position: Int): Int {
        val message = messages[position]
        val currentUserId = Singleton.instance.activeUser!!.id
         var result = if (message.sender.id == currentUserId) CARD_RIGHT else CARD_LEFT
        if (message.vote != null) {
            result *= if (message.vote is MapVote) CARD_MAP_VOTE else CARD_TEXT_VOTE
            if (message.vote.respondingIds.contains(Singleton.instance.activeUser!!.id))
                result *= CARD_VOTED
        }
        return result
    }

    override fun getItemCount(): Int = messages.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val message = messages[position]
        val viewType = getItemViewType(position)

        holder.bindText(message.text)
        holder.bindIcon(message.sender.avatar)
        if (viewType % CARD_MAP_VOTE == 0 || viewType % CARD_TEXT_VOTE == 0)
            holder.bindVoteLayout(viewType, message)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val icon: ImageView = itemView.findViewById(R.id.message_card_sender_photo)
        private val text: TextView = itemView.findViewById(R.id.message_card_text)
        private val voteLayout: LinearLayout = itemView.findViewById(R.id.message_content)

        fun bindText(messageText: String) {
            text.text = messageText
        }

        fun bindVoteLayout(viewType: Int, message: Message) {
                voteLayout.apply {
                    if (childCount > 1)
                        return

                    val vote: Vote
                    try {
                        vote = message.vote!! //?: Toast.makeText(context, "Error with vote", Toast.LENGTH_SHORT).show()
                    } catch (npe: NullPointerException) {
                        Toast.makeText(context, "Error with vote", Toast.LENGTH_SHORT).show()
                        return@apply
                    }

                    if (viewType % CARD_MAP_VOTE == 0) {
                        val mapOptions = GoogleMapOptions()
                                .liteMode(true)
                        val map = MapView(context, mapOptions).apply {
                            id = mMapId
                            layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 480)
                            onCreate(Bundle())
                            onResume()
                            MapsInitializer.initialize(context)
                            getMapAsync {
                                mGoogleMap = it
                                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(getLatLngBounds(vote), 0))
                                vote as MapVote
                                var index = 0
                                vote.points.forEach {
                                    mGoogleMap.addMarker(MarkerOptions()
                                            .title(vote.variants[index].first)
                                            .position(LatLng(it.first, it.second)))
                                    index++
                                }
                            }
                        }
                        addView(map)
                    }
                    val mMaxCheckedCount = maxCheckedCount(vote)
                    vote.variants.forEach {
                        val voteVariant = LayoutInflater.from(context).inflate(R.layout.vote_layout, this, false).apply {
                            findViewById<TextView>(R.id.vote_variant_text).text = it.first
                            findViewById<ProgressBar>(R.id.vote_count).apply {
                                max = mMaxCheckedCount
                                progress = it.second
                            }
                            if (viewType % CARD_VOTED == 0) {
                                findViewById<CheckBox>(R.id.vote_check_box).visibility = View.INVISIBLE
                            }
                        }
                        addView(voteVariant)
                    }
                    if (viewType % CARD_VOTED != 0) {
                        val voteButton = Button(context)
                        voteButton.text = "Vote"
                        voteButton.setOnClickListener { button ->
                            vote.respondingIds.add(Singleton.instance.activeUser!!.id)
                            val startIndex = if (viewType % CARD_MAP_VOTE != 0) 1 else 2
                            for (i in startIndex until childCount - 1) {
                                val index = i - startIndex
                                val voteVariant = getChildAt(i)
                                voteVariant.findViewById<CheckBox>(R.id.vote_check_box).apply {
                                    if (isChecked) {
                                        voteVariant.findViewById<ProgressBar>(R.id.vote_count).apply {
                                            if (progress == max) max++
                                            incrementProgressBy(1)
                                        }
                                        vote.variants[index] = Pair(vote.variants[index].first, vote.variants[index].second + 1)
                                    }
                                }
                            }
                            val newMaxChecked = maxCheckedCount(vote)
                            for (i in startIndex until childCount - 1) {
                                val voteVariant = getChildAt(i)
                                voteVariant.findViewById<ProgressBar>(R.id.vote_count).max = newMaxChecked
                            }
                            removeView(button)
                        }
                        addView(voteButton)
                    }
                }
        }

        fun bindIcon(avatar: Bitmap?) {
            if (avatar == null)
                launch(UI) {
                    if (mNoAvatarImage == null) {
                        icon.setImageResource(R.drawable.ic_no_avatar)
                        mNoAvatarImage = icon.drawable
                    }
                    else
                        icon.setImageDrawable(mNoAvatarImage)
                }
            else
                icon.setImageBitmap(avatar)

        }

        private fun maxCheckedCount(vote: Vote): Int {
            var mMaxCheckedCount = vote.variants[0].second
            vote.variants.forEach {
                if (it.second > mMaxCheckedCount)
                    mMaxCheckedCount = it.second
            }
            return mMaxCheckedCount
        }

        private fun getLatLngBounds(vote: Vote): LatLngBounds {
            vote as MapVote
            var minLat = vote.points.first().first
            var maxLat = vote.points.first().first
            var minLng = vote.points.first().second
            var maxLng = vote.points.first().second

            vote.points.forEach {
                if (minLat > it.first) minLat = it.first
                if (maxLat < it.first) maxLat = it.first
                if (minLng > it.second) minLng = it.second
                if (maxLng < it.second) maxLng = it.second
            }
            val latVector = maxLat - minLat
            val lngVector = maxLng - minLng
            minLat += -latVector * 0.05
            maxLat += latVector * 0.05
            minLng += -lngVector * 0.05
            maxLng += latVector * 0.05
            return LatLngBounds(LatLng(minLat, minLng), LatLng(maxLat, maxLng))
        }

    }
}