package com.ionexa.nextgsi.Pages

import android.widget.EditText
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

import com.ionexa.nextgsi.Components.RectangleWithCurve
import com.ionexa.nextgsi.DataClass.GoogleUserData

import com.ionexa.nextgsi.MVVM.ProfileMVVM
import com.ionexa.nextgsi.R
import com.ionexa.nextgsi.R.*
import com.ionexa.nextgsi.ui.theme.DarkSlateBlue


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfilePage(
    modifier: Modifier = Modifier,
    googleUserData: GoogleUserData? = null,
    naveController: NavController,
    ProfileViewModel: ProfileMVVM,
    LogOutPRofile: () -> Unit
) {
    var eidtable by remember { mutableStateOf(true) }
    var menueState by remember { mutableStateOf(false) }

    LaunchedEffect(true) {
        ProfileViewModel.updateprofileImageUrl(
            googleUserData?.profilePictureUrl
                ?: "https://static.vecteezy.com/system/resources/previews/002/002/403/large_2x/man-with-beard-avatar-character-isolated-icon-free-vector.jpg"
        )
        ProfileViewModel.updatname(googleUserData?.userName ?: "Name")
        ProfileViewModel.updateemail(googleUserData?.userId ?: "Email")
    }
    Box(modifier = modifier.fillMaxSize(1f)) {
        Column(modifier = modifier.fillMaxSize(1f)) {
            RectangleWithCurve(
                Modifier.fillMaxWidth(1f),
                profileimageurl = ProfileViewModel.profileImageUrl
            ) {
                eidtable = false
            }
            Row(Modifier.fillMaxWidth(1f), horizontalArrangement = Arrangement.Center) {
                TextField(
                    value = ProfileViewModel.name,
                    onValueChange = { ProfileViewModel.updatname(it) },
                    enabled = true,
                    readOnly = eidtable,
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
                    value = ProfileViewModel.address,
                    onValueChange = { ProfileViewModel.updateaddress(it) },
                    enabled = true,
                    readOnly = eidtable,
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
                    value = ProfileViewModel.phone,
                    onValueChange = { ProfileViewModel.updatephone(it) },
                    enabled = true,
                    readOnly = eidtable,
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
                    value = ProfileViewModel.password,
                    onValueChange = { ProfileViewModel.updatepassword(it) },
                    enabled = true,
                    readOnly = eidtable,
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
                    value = ProfileViewModel.email,
                    onValueChange = { ProfileViewModel.updateemail(it) },
                    enabled = true,
                    readOnly = eidtable,
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
                        colors = ButtonDefaults.buttonColors(containerColor = DarkSlateBlue)
                    ) {
                        Text(text = "Cancle")
                    }
                    Button(
                        onClick = { eidtable = true },
                        colors = ButtonDefaults.buttonColors(containerColor = DarkSlateBlue)
                    ) {
                        Text(text = "Save")
                    }
                }
        }// background
        AnimatedVisibility(
            visible = !menueState,
            enter = slideInHorizontally(),
            exit = slideOutHorizontally()
        ) {
            Card(
                modifier = Modifier
                    .width(50.dp)
                    .height(50.dp)
                    .offset(10.dp, 40.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.elevatedCardElevation(10.dp)
            )
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
        HeamBurger(state = menueState, hemState = {
            menueState = false
        }, logout = {
            LogOutPRofile()
        })
    }
}