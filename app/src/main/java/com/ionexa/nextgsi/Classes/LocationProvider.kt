package com.ionexa.nextgsi.Classes

import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Looper
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.LocationServices


class LocationProvider(private val context: Context) {

    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    fun getLastLocation(onLocationResult: (Location?) -> Unit) {
        if (ActivityCompat.checkSelfPermission(
                context,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Handle permission request
            return
        }

        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            onLocationResult(location)
        }
    }
}
