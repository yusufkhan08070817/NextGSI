package com.ionexa.nextgsi.SingleTon

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun getLocationName(latitude: String, longitude: String): String {
    Log.e("lat", latitude)
    Log.e("log", longitude)
    return withContext(Dispatchers.IO) {
        try {
            val response = nominatimApi.reverseGeocode(latitude, longitude)
            if (response.isSuccessful) {
                Locatation.gpslocatation=response.body()?.display_name?:"Unknown location"
                response.body()?.display_name ?: "Unknown location"
            } else {
                "Error: ${response.message()}"
            }
        } catch (e: Exception) {
            "Error: ${e.message}"
        }
    }
}
