package com.ionexa.nextgsi.SingleTon

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object common {

    val db = Firebase.firestore
    const val defaultpic="https://static.vecteezy.com/system/resources/previews/002/002/403/large_2x/man-with-beard-avatar-character-isolated-icon-free-vector.jpg"
val FBDBTAG="FBDBTages"
    @RequiresApi(Build.VERSION_CODES.O)
    fun getCurrentDateTime(): String {
        // Get the current date and time
        val currentDateTime = LocalDateTime.now()
        // Define the desired format
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        // Format the date and time and return it as a string
        return currentDateTime.format(formatter)
    }
}