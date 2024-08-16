package com.ionexa.nextgsi.DataClass

data class ReverseGeocodingResponse(
    val place_id: Long,
    val licence: String,
    val osm_type: String,
    val osm_id: Long,
    val lat: String,
    val lon: String,
    val `class`: String,  // Use backticks because `class` is a reserved keyword
    val type: String,
    val place_rank: Int,
    val importance: Double,
    val addresstype: String,
    val name: String,
    val display_name: String,
    val address: Address,
    val boundingbox: List<String>
) {
    data class Address(
        val region: String,
        val ISO3166_2_lvl4: String,  // Changed to match property naming conventions
        val postcode: String,
        val country: String,
        val country_code: String
    )
}