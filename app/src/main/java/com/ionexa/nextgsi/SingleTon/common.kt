package com.ionexa.nextgsi.SingleTon

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date

object common {

    val db = Firebase.firestore
    const val defaultpic="https://static.vecteezy.com/system/resources/previews/002/002/403/large_2x/man-with-beard-avatar-character-isolated-icon-free-vector.jpg"
val FBDBTAG="FBDBTages"


    fun getCurrentDateTime(): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Use LocalDateTime for API 26 (Oreo) and above
            val currentDateTime = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            currentDateTime.format(formatter)
        } else {
            // Use SimpleDateFormat for API levels below 26
            val currentDateTime = Date()
            val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            formatter.format(currentDateTime)
        }
    }
    fun replaceSpecialChars(email: String): String {
        // Define a regex pattern for special characters except '.' and '@'
        val specialCharsPattern = "[^a-zA-Z0-9]".toRegex()
        // Replace all special characters with "yk"
        return email.replace(specialCharsPattern, "")
    }
}