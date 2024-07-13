package com.ionexa.nextgsi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ionexa.nextgsi.MVVM.AuthViewModel
import com.ionexa.nextgsi.MVVM.Loginmvvm
import com.ionexa.nextgsi.Pages.LoginPage

import com.ionexa.nextgsi.Pages.Splashscreen
import com.ionexa.nextgsi.ui.theme.NextGsiTheme

class MainActivity : ComponentActivity() {
    private val LoginViewModel by viewModels<Loginmvvm>()
    private val AuthViewMOdel by viewModels<AuthViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navcontroller = rememberNavController()
            NavHost(navController = navcontroller, startDestination = "SplashScreen") {
                composable("LoginPage") {
                    NextGsiTheme {
                        LoginPage(LoginViewModel, AuthViewMOdel)
                    }
                }
                composable("SplashScreen") {
                    Splashscreen(modifier = Modifier, navcontroller)
                }

            }
        }
    }
}