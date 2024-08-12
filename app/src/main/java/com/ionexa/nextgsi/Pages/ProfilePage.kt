package com.ionexa.nextgsi.Pages

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.ionexa.nextgsi.Components.FilePicker
import com.ionexa.nextgsi.Components.RectangleWithCurve
import com.ionexa.nextgsi.DataClass.Customer
import com.ionexa.nextgsi.DataClass.GoogleUserData
import com.ionexa.nextgsi.DataClass.PersonalDetails
import com.ionexa.nextgsi.FBFireBase.FSDB
import com.ionexa.nextgsi.FBFireBase.FireBaseStorage
import com.ionexa.nextgsi.MVVM.ProfileMVVM
import com.ionexa.nextgsi.R
import com.ionexa.nextgsi.SingleTon.common
import com.ionexa.nextgsi.ui.theme.DarkSlateBlue
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfilePage(
    modifier: Modifier = Modifier,
    googleUserData: GoogleUserData? = null,
    naveController: NavController,
    ProfileViewModel: ProfileMVVM,
    LogOutPRofile: () -> Unit
) {
    val storage = FireBaseStorage()
    var onclickimage by remember { mutableStateOf(false) }
    var profiledata by remember { mutableStateOf(Customer("", "", "", "", "", "", "", "", "", "")) }
    val context = LocalContext.current
    var eidtable by remember { mutableStateOf(true) }
    var menueState by remember { mutableStateOf(false) }
    val fsdb = FSDB()
    val coroutineScope = rememberCoroutineScope()
    var fbauth = common.replaceSpecialChars(Firebase.auth.currentUser!!.email ?: "")
    LaunchedEffect(true) {
        ProfileViewModel.updateprofileImageUrl(
            googleUserData?.profilePictureUrl
                ?: "https://static.vecteezy.com/system/resources/previews/002/002/403/large_2x/man-with-beard-avatar-character-isolated-icon-free-vector.jpg"
        )
        ProfileViewModel.updatname(googleUserData?.userName ?: "Name")
        ProfileViewModel.updateemail(googleUserData?.userId ?: "Email")
    }


    LaunchedEffect(true) {
        Toast.makeText(
            context,
            "${if (common.myid.value.isEmpty()) fbauth else common.myid.value.toString()}",
            Toast.LENGTH_SHORT
        ).show()
        fsdb.getDataFromFireStoreDB("users",
            "${if (common.myid.value.isEmpty()) fbauth else common.myid.value.toString()}",
            onSuccess = {

                Log.e("TAG", "ProfilePage: $it")

                if (it != null) {

                    ;
                    var rawdataProfile = fsdb.hashMapToDataClass(
                        it["personel_info"] as HashMap<String, Any>, Customer::class
                    )
                    Log.e("TAG lund", "ProfilePage email: ${rawdataProfile!!.email}")

                    profiledata = rawdataProfile ?: Customer("", "", "", "", "", "", "", "", "", "")
                    ProfileViewModel.updatname(profiledata.name)
                    ProfileViewModel.updateemail(common.decodeFromBase64(profiledata.email))
                    ProfileViewModel.updatephone(profiledata.phone)
                    ProfileViewModel.updateaddress(profiledata.address)
                    ProfileViewModel.updateprofileImageUrl(common.decodeFromBase64(profiledata.profilePic))
                    ProfileViewModel.updatepassword(common.decodeFromBase64(profiledata.password))
                    ProfileViewModel.updaterole(profiledata.role)
                    ProfileViewModel.updateusername(profiledata.username)
                    ProfileViewModel.updatecreatedAt(profiledata.createdAt)
                    ProfileViewModel.updateid(profiledata.id)

                } else {
                    Toast.makeText(context, "null data", Toast.LENGTH_SHORT).show()
                }

            }) {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }

    if (onclickimage && !eidtable) {

        FilePicker {
            Toast.makeText(context, " ${it[0].uri}", Toast.LENGTH_LONG).show()

            coroutineScope.launch {
                storage.uploadSingleImage(imageUri = it[0].uri,
                    path = "users",
                    onProgress = {},
                    onFailure = {
                        Log.e("FBERROR", it.message.toString())
                    },
                    onSuccess = {
                        ProfileViewModel.updateprofileImageUrl(it)
                    })
            }
            if (it.isNotEmpty()) {
                onclickimage = false
            }
        }
    }

    Box(modifier = modifier.fillMaxSize(1f)) {
        Column(modifier = modifier.fillMaxSize(1f)) {
            RectangleWithCurve(Modifier.fillMaxWidth(1f),
                profileimageurl = ProfileViewModel.profileImageUrl,
                eidtable,
                onclick = {
                    onclickimage = true
                }) {
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
            if (!eidtable) Row(
                Modifier
                    .fillMaxWidth(1f)
                    .padding(vertical = 60.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Button(
                    onClick = {
                        eidtable = true

                        coroutineScope.launch {

                            fsdb.getDataFromFireStoreDB("users",
                                "${if (common.myid.value.isEmpty()) fbauth else common.myid.value.toString()}",
                                onSuccess = {

                                    Log.e("TAG", "ProfilePage: $it")

                                    if (it != null) {

                                        ;
                                        var rawdataProfile = fsdb.hashMapToDataClass(
                                            it["personel_info"] as HashMap<String, Any>, Customer::class
                                        )
                                        Log.e("TAG lund", "ProfilePage email: ${rawdataProfile!!.email}")

                                        profiledata = rawdataProfile ?: Customer("", "", "", "", "", "", "", "", "", "")
                                        ProfileViewModel.updatname(profiledata.name)
                                        ProfileViewModel.updateemail(common.decodeFromBase64(profiledata.email))
                                        ProfileViewModel.updatephone(profiledata.phone)
                                        ProfileViewModel.updateaddress(profiledata.address)
                                        ProfileViewModel.updateprofileImageUrl(common.decodeFromBase64(profiledata.profilePic))
                                        ProfileViewModel.updatepassword(common.decodeFromBase64(profiledata.password))
                                        ProfileViewModel.updaterole(profiledata.role)
                                        ProfileViewModel.updateusername(profiledata.username)
                                        ProfileViewModel.updatecreatedAt(profiledata.createdAt)
                                        ProfileViewModel.updateid(profiledata.id)

                                    } else {
                                        Toast.makeText(context, "null data", Toast.LENGTH_SHORT).show()
                                    }

                                }) {
                                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                            }
                        }


                    }, colors = ButtonDefaults.buttonColors(containerColor = DarkSlateBlue)
                ) {
                    Text(text = "Cancle")
                }
                Button(
                    onClick = {
                        eidtable = true
                        coroutineScope.launch {

                            val updateCustomer = Customer(
                                id = common.myid.value,
                                name = ProfileViewModel.name,
                                email = common.encodeToBase64(ProfileViewModel.email),
                                phone = ProfileViewModel.phone,
                                address = ProfileViewModel.address,
                                profilePic = common.encodeToBase64(ProfileViewModel.profileImageUrl),
                                password = common.encodeToBase64(ProfileViewModel.password),
                                role = profiledata.role,
                                createdAt = profiledata.createdAt,
                                username = profiledata.username
                            )
                            val customerhashmap = fsdb.run { updateCustomer.toHashMap() }
                            val uploadhashmap= hashMapOf<String,Any?>()
                            uploadhashmap.put("personel_info",customerhashmap)
                            fsdb.updateDataInFireStoreDB(
                                "users",
                                common.myid.value,
                                updates = uploadhashmap,
                                onSuccess = {
                                    Toast.makeText(context, "Updated", Toast.LENGTH_SHORT).show()
                                }) {
                                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()

                            }
                        }

                    }, colors = ButtonDefaults.buttonColors(containerColor = DarkSlateBlue)
                ) {
                    Text(text = "Save")
                }
            }
        }// background
        AnimatedVisibility(
            visible = !menueState, enter = slideInHorizontally(), exit = slideOutHorizontally()
        ) {
            Card(
                modifier = Modifier
                    .width(50.dp)
                    .height(50.dp)
                    .offset(10.dp, 40.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.elevatedCardElevation(10.dp)
            ) {

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