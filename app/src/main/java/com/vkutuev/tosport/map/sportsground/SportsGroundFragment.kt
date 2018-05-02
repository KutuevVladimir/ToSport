package com.vkutuev.tosport.map.sportsground

import android.app.Fragment
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.vkutuev.tosport.R
import com.vkutuev.tosport.Singleton
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch

class SportsGroundFragment : Fragment() {

    lateinit var mMenu: BottomNavigationView

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        mMenu = activity.findViewById(R.id.bottomNavigationView)
        mMenu.visibility = View.GONE

        val layout = inflater!!.inflate(R.layout.sportsground_layout, container, false)
        val bundle = arguments
        bundle?.let {
            val id = it.getInt("id")

            launch(UI) {
                val sportsGround  = async(CommonPool) {
                    Singleton.instance.serverAPI.getSportsGroundInformation(id)
                }.await()

                layout.findViewById<TextView>(R.id.sports_ground_title).text = getString(R.string.sports_ground_title, sportsGround?.sport.toString())
                layout.findViewById<TextView>(R.id.sports_ground_info).text =  getString(R.string.sports_ground_info, sportsGround?.information)
                layout.findViewById<TextView>(R.id.sports_ground_admin).text = getString(R.string.sports_ground_admin, sportsGround?.admin?.username)
            }
        }

        return layout
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mMenu.visibility = View.VISIBLE
    }
}