package com.vkutuev.tosport.map.sportsground

import android.Manifest
import android.app.Activity
import android.app.Fragment
import android.content.Intent

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.vkutuev.tosport.PermissionCodes
import com.vkutuev.tosport.R
import com.vkutuev.tosport.Singleton
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import java.io.FileNotFoundException

class SportsGroundFragment : Fragment() {

    private lateinit var mMenu: BottomNavigationView
    private lateinit var mImage: ImageView
    private var mId: Int = -1

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        mMenu = activity.findViewById(R.id.bottomNavigationView)
        mMenu.visibility = View.GONE

        val layout = inflater!!.inflate(R.layout.sportsground_layout, container, false)
        mImage = layout.findViewById(R.id.sports_ground_photo)
        val bundle = arguments
        bundle?.let {
            mId = it.getInt("id")

            launch(UI) {
                val sportsGround  = async(CommonPool) {
                    Singleton.instance.serverAPI.getSportsGroundInformation(mId)
                }.await()

                layout.findViewById<TextView>(R.id.sports_ground_title).text = getString(R.string.sports_ground_title, sportsGround?.sport.toString())
                layout.findViewById<TextView>(R.id.sports_ground_info).text =  getString(R.string.sports_ground_info, sportsGround?.information)
                layout.findViewById<TextView>(R.id.sports_ground_admin).text = getString(R.string.sports_ground_admin, sportsGround?.admin?.username)
                sportsGround?.photo?.let {
                    mImage.setImageBitmap(it)
                }

                mImage.setOnClickListener {
                    requestPermissions(
                            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                            PermissionCodes.Gallery.permissionCode
                    )
                }
            }
        }

        return layout
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mMenu.visibility = View.VISIBLE
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            PermissionCodes.Gallery.permissionCode -> {
                if (grantResults.isNotEmpty() && permissions[0] == android.Manifest.permission.READ_EXTERNAL_STORAGE) {
                    val intent = Intent(Intent.ACTION_PICK)
                    intent.type = "image/*"
                    startActivityForResult(intent, PermissionCodes.Gallery.permissionCode)
                } else {
                    // Permission was denied. Display an error message.
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == PermissionCodes.Gallery.permissionCode && resultCode == Activity.RESULT_OK && data != null) {
            val uri = data.data

            try {
                val inputStream = activity.contentResolver.openInputStream(uri!!)
                val bitmap = BitmapFactory.decodeStream(inputStream)
                mImage.setImageBitmap(bitmap)
                async {
                    Singleton.instance.serverAPI.setSportsGroundPhoto(mId, bitmap)
                }
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }

        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}