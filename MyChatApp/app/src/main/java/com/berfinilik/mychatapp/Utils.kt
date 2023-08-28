package com.berfinilik.mychatapp

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import com.berfinilik.mychatapp.notifications.FirebaseService.Companion.token
import com.berfinilik.mychatapp.notifications.entity.Token
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

class Utils {
    companion object {
        @SuppressLint("StaticFieldLeak")
        val context = MyApplication.instance.applicationContext
        @SuppressLint("StaticFieldLeak")
        val firestore = FirebaseFirestore.getInstance()

        private val auth = FirebaseAuth.getInstance()
        private var userid: String = ""
        const val REQUEST_IMAGE_CAPTURE = 1
        const val REQUEST_IMAGE_PICK = 2
        const val MESSAGE_RIGHT = 1
        const val MESSAGE_LEFT = 2
        const val CHANNEL_ID = "com.berfinilik.mychatapp"


        fun getUidLoggedIn(): String {

            if (auth.currentUser!=null){
                userid = auth.currentUser!!.uid
            }
            return userid
        }

        fun getTime(): String {
            val formatter = SimpleDateFormat("HH:mm:ss")
            val date: Date = Date(System.currentTimeMillis())
            val stringdate = formatter.format(date)
            return stringdate

        }
    }
}