package com.vkutuev.tosport

import android.app.Fragment
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import com.vkutuev.tosport.chats.ChatsNavigationFragment
import com.vkutuev.tosport.map.MapFragment


class MainActivity : AppCompatActivity() {

    private lateinit var mNavigation: BottomNavigationView
    private var mCurrentFragmentId: Int = R.layout.chats_layout

    private val mMapNavigationFragment = MapFragment()
    private val mChatsNavigationFragment = ChatsNavigationFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mNavigation = findViewById(R.id.bottomNavigationView)
        mNavigation.selectedItemId = R.id.navigation_account
        mNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        Singleton.instance.fragmentManager = fragmentManager
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener {
        var fragment: Fragment? = null
        val result = when (it.itemId) {
            R.id.navigation_map -> {
                if (mCurrentFragmentId != R.id.navigation_map) {
                    mCurrentFragmentId = R.id.navigation_map
                    fragment = mMapNavigationFragment
                }
                true
            }
            R.id.navigation_chats -> {
                if (mCurrentFragmentId != R.id.navigation_chats) {
                    mCurrentFragmentId = R.id.navigation_chats
                    fragment = mChatsNavigationFragment
                }
                true
            }
            R.id.navigation_account -> {
                if (mCurrentFragmentId != R.id.navigation_account) {
                    mCurrentFragmentId = R.id.navigation_account
                    fragment = null
                }

                true
            }
            else ->false
        }
        val fragmentManager = Singleton.instance.fragmentManager
        fragment?.let {
            val backStackCount = fragmentManager.backStackEntryCount

            for (i in 0 until backStackCount) {
                val backStackId = fragmentManager.getBackStackEntryAt(i).id
                fragmentManager.popBackStack(backStackId, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            }

            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, it)
                    .addToBackStack(it.toString())
                    .commit()
        }
        result
    }
}
