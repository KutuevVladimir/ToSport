package com.vkutuev.tosport.chats.messages

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.widget.Button
import android.widget.CheckBox
import android.widget.ProgressBar
import android.widget.TextView
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.vkutuev.tosport.R
import com.vkutuev.tosport.Singleton
import com.vkutuev.tosport.model.MapVote
import com.vkutuev.tosport.model.Vote

const val BUTTON = 0
const val VOTE_VARIANT = 1
const val MAP = 2

class VoteRecyclerViewAdapter(private val vote: Vote) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), OnMapReadyCallback {

    private val mPositionOffset = if (vote is MapVote) -1 else 0
    private val mMaxVotesCount: Int
        get() {
            var temp = vote.variants[0].second
            vote.variants.forEach {
                if (it.second > temp)
                    temp = it.second
            }
            return temp
        }
    private val mCheckedVariants = HashSet<Int>()

    private val mMapId = View.generateViewId()
    private lateinit var mGoogleMap: GoogleMap
    private val cameraPosition: LatLng by lazy {
        var latitude = 0.0
        var longitude = 0.0
        if (vote is MapVote) {
            vote.points.forEach {
                latitude += it.first
                longitude += it.second
            }
            latitude /= vote.points.size
            longitude /= vote.points.size
        }
        return@lazy LatLng(latitude, longitude)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
            when (viewType) {
                VOTE_VARIANT -> {
                    val view = LayoutInflater.from(parent.context).inflate(R.layout.vote_layout, parent, false)
                    VoteElementViewHolder(view)
                }
                MAP -> {
//                    val mapFragmentView = LayoutInflater
//                            .from(parent.context)
//                            .inflate(R.layout.map_layout, parent, false)!!
//                    MapViewHolder(mapFragmentView)
                    val mapOptions = GoogleMapOptions()
                            .liteMode(true)
                            .camera(CameraPosition(cameraPosition, 10f, 0f, 0f))
                    val map = MapView(parent.context, mapOptions).apply {
                        id = mMapId
                        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, 480)
                    }
                    MapViewHolder(map)
                }
                else -> ButtonViewHolder(Button(parent.context))
            }

    override fun getItemViewType(position: Int): Int =
        when {
            vote is MapVote -> when (position) {
                0 -> MAP
                vote.variants.size + 1 -> BUTTON
                else -> VOTE_VARIANT
            }
            position == vote.variants.size-> BUTTON
            else -> VOTE_VARIANT
        }

    override fun getItemCount(): Int {
        var count = vote.variants.size
        if (!vote.respondingIds.contains(Singleton.instance.activeUser!!.id))
            count++
        if (vote is MapVote)
            count++
        return count
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            VOTE_VARIANT -> {
                val index = position + mPositionOffset
                holder as VoteElementViewHolder
                holder.text?.text = "${position + 1}) ${vote.variants[index].first}"
                holder.progressBar?.apply {
                    max = mMaxVotesCount
                    progress = vote.variants[index].second
                }

                if (vote.respondingIds.contains(Singleton.instance.activeUser!!.id))
                    holder.checkBox?.visibility = View.GONE
                else
                    holder.checkBox?.setOnCheckedChangeListener { buttonView, isChecked ->
                        if (isChecked)
                            mCheckedVariants.add(index)
                        else
                            mCheckedVariants.remove(index)
                    }
            }
            MAP -> {
                holder as MapViewHolder
                holder.map.apply {
                    onCreate(Bundle())
                    onResume()
                    MapsInitializer.initialize(context)
                    getMapAsync(this@VoteRecyclerViewAdapter)
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
        mCheckedVariants.forEach {
            vote.variants[it] = Pair(vote.variants[it].first, vote.variants[it].second + 1)
        }
        notifyDataSetChanged()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mGoogleMap = googleMap
        vote as MapVote
        var index = 0
        vote.points.forEach {
            mGoogleMap.addMarker(MarkerOptions()
                    .title(vote.variants[index].first)
                    .position(LatLng(it.first, it.second)))
            index++
        }
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(getLatLngBounds(), 0));
    }

    private fun getLatLngBounds(): LatLngBounds {
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

    class VoteElementViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val text: TextView? = itemView.findViewById(R.id.vote_variant_text)
        val progressBar: ProgressBar? = itemView.findViewById(R.id.vote_count)
        val checkBox: CheckBox? = itemView.findViewById(R.id.vote_check_box)
    }

    class ButtonViewHolder(val button: Button) : RecyclerView.ViewHolder(button)

    class MapViewHolder(val map: MapView) : RecyclerView.ViewHolder(map) {
//        val map: MapView? = map.findViewById(R.id.map)
    }
}