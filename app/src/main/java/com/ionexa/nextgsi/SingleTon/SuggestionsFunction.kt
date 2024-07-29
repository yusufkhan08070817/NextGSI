package com.ionexa.nextgsi.SingleTon

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun getSuggestions(query: String): List<String> {
    return withContext(Dispatchers.IO) {
        try {
            val response = nominatimApi.getLocationSuggestions(query)
            if (response.isSuccessful) {
                val locations = response.body() ?: emptyList()
                locations.map { it.display_name }
            } else {
                listOf("Error: ${response.message()}")
            }
        } catch (e: Exception) {
            listOf("Error: ${e.message}")
        }
    }
}