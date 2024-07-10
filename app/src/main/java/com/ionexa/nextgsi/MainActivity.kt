package com.ionexa.nextgsi

import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.Visibility
import com.ionexa.nextgsi.LoginComponents.ButtonWithCutCornerShape
import com.ionexa.nextgsi.LoginComponents.Combined
import com.ionexa.nextgsi.LoginComponents.Email
import com.ionexa.nextgsi.LoginComponents.LogWithGoogleFacebookApple
import com.ionexa.nextgsi.LoginComponents.Login
import com.ionexa.nextgsi.LoginComponents.Logn_Register
import com.ionexa.nextgsi.LoginComponents.Password
import com.ionexa.nextgsi.LoginComponents.RememberAndForgot
import com.ionexa.nextgsi.ui.theme.Next
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
                    modifier = Modifier.align(Alignment.BottomCenter).fillMaxWidth(1f).offset(50.dp,-10.dp).rotate(-10f),
                    contentScale = ContentScale.Crop
                )
                    Image(
                        painter = painterResource(id = R.drawable.curve2),
                        contentDescription = null,
                        modifier = Modifier.align(Alignment.BottomCenter).fillMaxWidth(1f).offset(0.dp,-10.dp),
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
                        ButtonWithCutCornerShape("emeai", "password") { email, password -> }
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





