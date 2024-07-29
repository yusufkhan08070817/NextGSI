package com.ionexa.nextgsi.Interface

import com.ionexa.nextgsi.DataClass.NominatimResponseItem
import com.ionexa.nextgsi.DataClass.ReverseGeocodingResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface NominatimApi {
    @GET("search")
    suspend fun getLocationSuggestions(
        @Query("q") location: String,
        @Query("format") format: String = "json"
    ): Response<List<NominatimResponseItem>>

    @GET("reverse")
    suspend fun reverseGeocode(
        @Query("lat") latitude: String,
        @Query("lon") longitude: String,
        @Query("format") format: String = "json"
    ): Response<ReverseGeocodingResponse>
}