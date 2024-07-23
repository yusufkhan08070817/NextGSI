package com.ionexa.nextgsi.Pages

import android.widget.EditText
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ionexa.nextgsi.Components.HeamBurger
import com.ionexa.nextgsi.Components.RectangleWithCurve
import com.ionexa.nextgsi.R
import com.ionexa.nextgsi.R.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfilePage(modifier: Modifier = Modifier, naveController: NavController) {
    var eidtable by remember { mutableStateOf(true) }
    var menueState by remember { mutableStateOf(false) }
    Box(modifier = modifier.fillMaxSize(1f)) {
        Column(modifier = modifier.fillMaxSize(1f)) {
            RectangleWithCurve(Modifier.fillMaxWidth(1f)) {
                eidtable = false
            }
            Row(Modifier.fillMaxWidth(1f), horizontalArrangement = Arrangement.Center) {
                TextField(
                    value = "Name", onValueChange = {}, enabled = true, readOnly = eidtable,
                    leadingIcon = {
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(imageVector = Icons.Default.Person, contentDescription = "email")
                        }
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.Transparent,
                        cursorColor = Color.Black,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )
            }
            Row(Modifier.fillMaxWidth(1f), horizontalArrangement = Arrangement.Center) {
                TextField(
                    value = "Address", onValueChange = {}, enabled = true, readOnly = eidtable,
                    leadingIcon = {
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(imageVector = Icons.Default.Place, contentDescription = "email")
                        }
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.Transparent,
                        cursorColor = Color.Black,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )
            }
            Row(Modifier.fillMaxWidth(1f), horizontalArrangement = Arrangement.Center) {
                TextField(
                    value = "Phone", onValueChange = {}, enabled = true, readOnly = eidtable,
                    leadingIcon = {
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(imageVector = Icons.Default.Phone, contentDescription = "email")
                        }
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.Transparent,
                        cursorColor = Color.Black,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )
            }
            Row(Modifier.fillMaxWidth(1f), horizontalArrangement = Arrangement.Center) {
                TextField(
                    value = "password", onValueChange = {}, enabled = true, readOnly = eidtable,
                    leadingIcon = {
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(imageVector = Icons.Default.Lock, contentDescription = "email")
                        }
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.Transparent,
                        cursorColor = Color.Black,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )
            }
            Row(Modifier.fillMaxWidth(1f), horizontalArrangement = Arrangement.Center) {
                TextField(
                    value = "Email", onValueChange = {}, enabled = true, readOnly = eidtable,
                    leadingIcon = {
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(imageVector = Icons.Default.Email, contentDescription = "email")
                        }
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.Transparent,
                        cursorColor = Color.Black,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )
            }
            if (!eidtable)
                Row(
                    Modifier
                        .fillMaxWidth(1f)
                        .padding(vertical = 60.dp), horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Button(
                        onClick = { eidtable = true },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0386D0))
                    ) {
                        Text(text = "Cancle")
                    }
                    Button(
                        onClick = { eidtable = true },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0386D0))
                    ) {
                        Text(text = "Save")
                    }
                }
        }// background
        AnimatedVisibility(visible = !menueState, enter = slideInHorizontally(), exit = slideOutHorizontally()) {
            Card(modifier = Modifier
                .width(50.dp)
                .height(50.dp).offset(10.dp,40.dp), colors = CardDefaults.cardColors(containerColor = Color.White), elevation = CardDefaults.elevatedCardElevation(10.dp))
            {

                IconButton(onClick = { menueState = true }) {
                    Icon(
                        painter = painterResource(id = R.drawable.menueicon),
                        contentDescription = "HeamBurger",
                        modifier = Modifier
                            .padding(10.dp)
                            .width(80.dp)
                            .height(80.dp)
                    )

                }
            }
        }
        HeamBurger(state =  menueState, hemState = {
            menueState=false
        })
    }
}