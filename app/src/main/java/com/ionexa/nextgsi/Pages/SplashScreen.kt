package com.ionexa.nextgsi.Pages

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.airbnb.lottie.compose.*
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.ionexa.nextgsi.R
import com.ionexa.nextgsi.SingleTon.NaveLabels
import com.ionexa.nextgsi.SingleTon.Navigation
import com.ionexa.nextgsi.SingleTon.common
import com.ionexa.nextgsi.SingleTon.common.db
import kotlinx.coroutines.delay

@Composable
fun Splashscreen(modifier: Modifier = Modifier, navController: NavController) {
    Column(
        modifier = modifier.fillMaxHeight(1f),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        LogoAppearAnimation(navController)
    }
}

@Composable
fun LogoAppearAnimation(navController: NavController) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loading))
    var isPlaying by remember { mutableStateOf(true) }
    var animationCompleted by remember { mutableStateOf(false) }
var context= LocalContext.current
    var TAG="splashlund"
    // Listen to animation state
    LaunchedEffect(composition) {
        composition?.let {
            val durationMillis = it.duration
            if (durationMillis != null) {
                delay(durationMillis.toLong())
                isPlaying = false
                animationCompleted = true
            }
        }
    }




    if (animationCompleted) {
        if (Firebase.auth.currentUser != null)
        {
            if (Firebase.auth.currentUser?.email!=null)
            {
                val vv= common.replaceSpecialChars(
                    common.replaceSpecialChars(Firebase.auth.currentUser?.email!!)
                )
                Log.e("lunnnd",vv)
                Toast.makeText(context, vv, Toast.LENGTH_SHORT).show()
                val docRef = db.collection("users").document(vv)
                docRef.get().addOnSuccessListener { document ->
                    if (document != null) {
                        var data = document.data
                        var profile = data?.get("personel_info") as Map<String, Any>
                        var roll = profile["role"].toString()
                        var address=profile["address"].toString()
                        common.locatation=address
                        common.roll = roll
                        if (common.roll == "customer") {
                            Navigation.navController.navigate(NaveLabels.Home)
                            NaveLabels.DefaultLoag = NaveLabels.Home
                        }
                        else {
                            if (common.roll == "seller") {
                                Navigation.navController.navigate(NaveLabels.seller)
                                NaveLabels.DefaultLoag = NaveLabels.seller
                            }
                            // else NaveLabels.DefaultLoag = NaveLabels.SplashScreen
                        }
                        Log.d(TAG, "DocumentSnapshot data: ${profile["role"]}")
                        Log.e("common.roll", common.roll)
                    } else {
                        Log.d(TAG, "No such document")
                    }
                }.addOnFailureListener { exception ->
                    Log.d(TAG, "get failed with ", exception)
                }
            }else
            {
                navController.navigate(NaveLabels.Login)
            }


        }
        else
        navController.navigate(NaveLabels.Login)

    }

    LottieAnimation(
        composition = composition,
        isPlaying = isPlaying,
        iterations = 1
    )
}
