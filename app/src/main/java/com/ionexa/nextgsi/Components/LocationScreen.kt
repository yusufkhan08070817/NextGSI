package com.ionexa.nextgsi.Components

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.location.LocationManager
import android.provider.Settings
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.ionexa.nextgsi.Classes.LocationProvider
import com.ionexa.nextgsi.MVVM.MapeKCMVVM
import com.ionexa.nextgsi.SingleTon.getLocationName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun LocationScreen(locationProvider: LocationProvider, Mapekcmvm: MapeKCMVVM) {
    val context = LocalContext.current
    val permissions = arrayOf(
        android.Manifest.permission.ACCESS_FINE_LOCATION,
        android.Manifest.permission.ACCESS_COARSE_LOCATION,
        android.Manifest.permission.READ_EXTERNAL_STORAGE
    )

    var allPermissionsGranted by remember { mutableStateOf(checkPermissions(permissions, context)) }
    var isLocationEnabled by rememberLocationEnabledState(context)
    val coroutineScope = rememberCoroutineScope()
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissionsMap ->
        allPermissionsGranted = permissionsMap.values.all { it }
        if (allPermissionsGranted && isLocationEnabled) {
            Log.d("LocationScreen", "All permissions granted and location enabled")
            fetchLocation(locationProvider, Mapekcmvm,coroutineScope)
        } else if (!allPermissionsGranted) {
            Log.d("LocationScreen", "Permissions denied: ${permissionsMap.filterValues { !it }}")
        }
    }

    LaunchedEffect(allPermissionsGranted, isLocationEnabled) {
        if (!allPermissionsGranted) {
            launcher.launch(permissions)
        } else if (allPermissionsGranted && !isLocationEnabled) {
            context.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
        }
    }

    // Effect to fetch location when location is enabled
    LaunchedEffect(isLocationEnabled) {
        if (isLocationEnabled) {
            // Delay added to allow time for location settings to apply
            kotlinx.coroutines.delay(2000) // 2 seconds delay
            fetchLocation(locationProvider, Mapekcmvm,coroutineScope)
        }
    }


}

@Composable
fun rememberLocationEnabledState(context: Context): MutableState<Boolean> {
    val isLocationEnabled = remember { mutableStateOf(isLocationEnabled(context)) }

    DisposableEffect(context) {
        val receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                isLocationEnabled.value = isLocationEnabled(context!!)
            }
        }
        val filter = IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION)
        context.registerReceiver(receiver, filter)

        onDispose {
            context.unregisterReceiver(receiver)
        }
    }

    return isLocationEnabled
}

private fun checkPermissions(permissions: Array<String>, context: Context): Boolean {
    return permissions.all {
        ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
    }
}

private fun isLocationEnabled(context: Context): Boolean {
    val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
}

private fun fetchLocation(locationProvider: LocationProvider, mapekcmvm: MapeKCMVVM, coroutineScope: CoroutineScope) {
    locationProvider.getLastLocation { loc ->
        loc?.let {
            mapekcmvm.updateYourLocatation(it.latitude, it.longitude)
            coroutineScope.launch {
                val locName = getLocationName(it.latitude.toString(), it.longitude.toString())
                mapekcmvm.updateyourcurrentLocatationString(locName)
            }
        }
    }
}
