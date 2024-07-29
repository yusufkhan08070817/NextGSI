package com.ionexa.nextgsi.SingleTon

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun getLocationName(latitude: String, longitude: String): String {
    return withContext(Dispatchers.IO) {
        try {
            val response = nominatimApi.reverseGeocode(latitude, longitude)
            if (response.isSuccessful) {
                response.body()?.display_name ?: "Unknown location"
            } else {
                "Error: ${response.message()}"
            }
        } catch (e: Exception) {
            "Error: ${e.message}"
        }
    }
}
