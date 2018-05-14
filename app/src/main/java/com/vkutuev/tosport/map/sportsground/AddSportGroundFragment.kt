package com.vkutuev.tosport.map.sportsground

import android.app.Fragment
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.vkutuev.tosport.R

class AddSportGroundFragment : Fragment(), OnMapReadyCallback {
    lateinit var mBottomMenu: BottomNavigationView

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val layout = inflater!!.inflate(R.layout.add_sports_ground_layout, container, false)

        mBottomMenu = activity.findViewById(R.id.bottomNavigationView)
        mBottomMenu.visibility = View.GONE

        return layout
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mBottomMenu.visibility = View.VISIBLE

    }

    override fun onMapReady(p0: GoogleMap?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}