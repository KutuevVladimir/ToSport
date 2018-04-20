package com.vkutuev.tosport.map

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.vkutuev.tosport.R
import com.vkutuev.tosport.Singleton

class SportsGroundFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val layout = inflater!!.inflate(R.layout.sportsground_layout, container, false)
        val bundle = arguments
        bundle?.let {
            val id = it.getInt("id")
            val sportsGround = Singleton.instance.serverAPI.getSportsGroundInformation(id)
            layout.findViewById<TextView>(R.id.sports_ground_title).text = getString(R.string.sports_ground_title, sportsGround.sport.toString())
            layout.findViewById<TextView>(R.id.sports_ground_info).text =  getString(R.string.sports_ground_info, sportsGround.information)
            layout.findViewById<TextView>(R.id.sports_ground_admin).text = getString(R.string.sports_ground_admin)
        }

        return layout
    }
}