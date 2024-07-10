package com.ionexa.nextgsi

import android.os.Bundle
import android.widget.Toast


import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Spacer

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier

import androidx.compose.ui.draw.rotate

import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource

import androidx.compose.ui.unit.dp

import com.ionexa.nextgsi.LoginComponents.ButtonWithCutCornerShape
import com.ionexa.nextgsi.LoginComponents.Combined
import com.ionexa.nextgsi.LoginComponents.Email
import com.ionexa.nextgsi.LoginComponents.LogWithGoogleFacebookApple
import com.ionexa.nextgsi.LoginComponents.Login
import com.ionexa.nextgsi.LoginComponents.Logn_Register
import com.ionexa.nextgsi.LoginComponents.Password
import com.ionexa.nextgsi.LoginComponents.RememberAndForgot
import com.ionexa.nextgsi.ui.theme.NextGsiTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NextGsiTheme {
                Box(modifier = Modifier.fillMaxSize(1f))
                {
                    Image(
                    painter = painterResource(id = R.drawable.curve),
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth(1f)
                        .offset(
                            50.dp,
                            (-10).dp
                        )
                        .rotate(-10f),
                    contentScale = ContentScale.Crop
                )
                    Image(
                        painter = painterResource(id = R.drawable.curve2),
                        contentDescription = null,
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .fillMaxWidth(1f)
                            .offset(
                                0.dp,
                                (-10).dp
                            ),
                        contentScale = ContentScale.Crop
                    )


                    Column {
                        Login()
                        Combined(Modifier)
                        Logn_Register(Modifier)
                        Email()
                        Spacer(modifier = Modifier.height(30.dp))
                        Password()
                        RememberAndForgot()
                        Spacer(modifier = Modifier.height(10.dp))
                        ButtonWithCutCornerShape("email", "password") { email, password ->
                            Toast.makeText(this@MainActivity, "$email $password", Toast.LENGTH_SHORT).show()
                        }
                        LogWithGoogleFacebookApple(
                            GoogleAuth = {
                                // Handle Google login
                                println("Google login clicked")
                            },
                            FaceBookAuth = {
                                // Handle Facebook login
                                println("Facebook login clicked")
                            },
                            AppleAuth = {
                                // Handle Apple login
                                println("Apple login clicked")
                            }
                        )

                    }

                }
            }
        }
    }
}





