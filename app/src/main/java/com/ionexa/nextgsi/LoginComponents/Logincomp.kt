package com.ionexa.nextgsi.LoginComponents

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ionexa.nextgsi.R
import com.ionexa.nextgsi.ui.theme.Next

@Composable
fun Login() {
    Column(
        modifier = Modifier
            .fillMaxWidth(1f)
            .height(90.dp)
            .offset(0.dp, 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center

    )
    {
        Text(
            text = "Login", style = TextStyle(
                fontSize = 40.sp,
                fontFamily = Next,

                )

        )
    }
}


@Composable
fun Logn_Register(modifier: Modifier = Modifier) {
    var isLoginSelected by remember { mutableStateOf(true) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 40.dp),

        horizontalArrangement = Arrangement.Center
    ) {
        TextWithUnderLine(
            state = isLoginSelected,
            text = "Login",
            onclick = { isLoginSelected = true }
        )
        Spacer(modifier = Modifier.width(16.dp)) // Add some spacing between the texts
        TextWithUnderLine(
            state = !isLoginSelected,
            text = "Register",
            onclick = { isLoginSelected = false }
        )
    }
}

@Composable
fun TextWithUnderLine(
    modifier: Modifier = Modifier,
    state: Boolean,
    text: String,
    onclick: () -> Unit
) {
    val textColor = if (state) Color(0xFF0386D0) else Color.Gray
    Text(
        text = text,
        color = textColor,
        fontFamily = Next, fontSize = 30.sp,
        modifier = modifier
            .clickable { onclick() }
            .drawBehind {
                if (state) {
                    val strokeWidth = 2.dp.toPx() // Bottom border thickness
                    val y = size.height - strokeWidth / 2
                    drawLine(
                        color = Color(0xFF0386D0),
                        start = Offset(0f, y),
                        end = Offset(size.width, y),
                        strokeWidth = strokeWidth
                    )
                }
            }
            .padding(bottom = if (state) 4.dp else 0.dp) // Optional: Add padding below text when border is present
    )
}

@Composable
fun Combined(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center
    ) {
        Text(
            buildAnnotatedString {
                append(
                    AnnotatedString(
                        text = "By signing in you are agreeing to our ",
                        spanStyle = SpanStyle(
                            fontFamily = Next,
                            fontSize = 25.sp,
                            color = Color.LightGray
                        )
                    )
                )
                append(
                    AnnotatedString(
                        text = "Terms and privacy policy",
                        spanStyle = SpanStyle(
                            fontFamily = Next,
                            fontSize = 26.sp,
                            color = Color(0xFF0386D0)
                        )
                    )
                )
            },
            modifier = Modifier.padding(horizontal = 40.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Email(modifier: Modifier = Modifier) {
    var Email by remember { mutableStateOf("") }

    val color1 = Color(0xFFFFFFFF)
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 40.dp)
    ) {

        Box() {
            TextField(
                value = Email,
                onValueChange = { Email = it },

                leadingIcon = {
                    IconButton(onClick = { }) {
                        Icon(imageVector = Icons.Filled.Email, contentDescription = "Email")
                    }
                },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = color1,
                    cursorColor = Color.Black

                )


            )


        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Password(modifier: Modifier = Modifier) {
    var Password by remember { mutableStateOf("") }
    var Istoggal by remember { mutableStateOf(false) }
    val color1 = Color(0xFFFFFFFF)
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 40.dp)
    ) {

        Box() {
            TextField(
                value = Password,
                onValueChange = { Password = it },

                leadingIcon = {
                    IconButton(onClick = { }) {
                        Icon(imageVector = Icons.Filled.Lock, contentDescription = "Email")
                    }
                },
                trailingIcon = {
                    Image(painter = if (Istoggal) painterResource(id = R.drawable.hide) else painterResource(
                        id = R.drawable.view
                    ),
                        contentDescription = "password",
                        modifier = Modifier
                            .width(20.dp)
                            .height(20.dp)
                            .clickable { Istoggal = !Istoggal })
                },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = color1,
                    cursorColor = Color.Black,
                    unfocusedTrailingIconColor = color1,
                    focusedTrailingIconColor = color1,
                    disabledTrailingIconColor = color1,

                    )


            )


        }
    }
}

@Composable
fun RememberAndForgot(modifier: Modifier = Modifier) {
    var IsChecked by remember {
        mutableStateOf(false)
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth(1f)
    ) {
        Row(horizontalArrangement = Arrangement.SpaceBetween) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(checked = IsChecked, onCheckedChange = { IsChecked = !IsChecked })

                Text(
                    text = "Remember password", style = TextStyle(
                        fontSize = 13.sp
                    )
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically) {


                Text(
                    text = "Forget password",
                    modifier = Modifier.padding(15.dp, 15.dp),
                    style = TextStyle(

                        color = Color(0xFF0386D0)
                    )
                )
            }
        }

    }
}

@Composable
fun ButtonWithCutCornerShape(Email:String,Passwerd:String, auth:(Email:String,Password:String)->Unit) {

    Button(
        onClick = {auth(Email,Passwerd)},
        colors = ButtonDefaults.buttonColors(Color(0xFF0386D0)),
        shape = CutCornerShape(10),
        modifier = Modifier.padding(horizontal = 30.dp)
    ) {
        Text(
            text = "Login",
            modifier = Modifier.fillMaxWidth(1f),
            textAlign = TextAlign.Center,
            style = TextStyle(
                color = Color.White,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = Next,

                )

        )

    }

}
@Composable
fun LogWithGoogleFacebookApple(
    modifier: Modifier = Modifier,
    GoogleAuth: () -> Unit,
    FaceBookAuth: () -> Unit,
    AppleAuth: () -> Unit
) {
    var height = 40.dp
    var width = 40.dp
    Text(text = "or", modifier = Modifier.fillMaxWidth(1f), textAlign = TextAlign.Center)
    Column(
        modifier = Modifier
            .fillMaxWidth(1f)
            .padding(horizontal = 30.dp),
        horizontalAlignment = CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row {
            Image(painter = painterResource(id = R.drawable.google),
                contentDescription = "Login With Google",
                modifier = Modifier
                    .height(height)
                    .width(width)
                    .padding(5.dp)
                    .clickable { GoogleAuth() })
            Image(painter = painterResource(id = R.drawable.apple),
                contentDescription = "Login With Google",
                modifier = Modifier
                    .height(height)
                    .width(width)
                    .padding(5.dp)
                    .clickable { AppleAuth() })
            Image(painter = painterResource(id = R.drawable.facebook),
                contentDescription = "Login With Google",
                modifier = Modifier
                    .height(height)
                    .width(width)
                    .padding(5.dp)
                    .clickable { FaceBookAuth() })


        }
    }

}

