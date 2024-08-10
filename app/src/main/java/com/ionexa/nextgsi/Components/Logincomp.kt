package com.ionexa.nextgsi.Components

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
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
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
import com.ionexa.nextgsi.ui.theme.BalckeshPurple
import com.ionexa.nextgsi.ui.theme.DarkOrchid
import com.ionexa.nextgsi.ui.theme.IndigoHeading

import com.ionexa.nextgsi.ui.theme.Next
import com.ionexa.nextgsi.ui.theme.RebeccaPurpleHilghtText

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
color = BalckeshPurple
                )

        )
    }
}


@Composable
fun Logn_Register(
    modifier: Modifier = Modifier,
    isLoginSelected: Boolean,
    onLoginSelectedChange: () -> Unit
) {


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 40.dp),

        horizontalArrangement = Arrangement.Center
    ) {
        TextWithUnderLine(
            state = isLoginSelected,
            text = "Login",
            onclick = { onLoginSelectedChange() }
        )
        Spacer(modifier = Modifier.width(16.dp)) // Add some spacing between the texts
        TextWithUnderLine(
            state = !isLoginSelected,
            text = "Register",
            onclick = { onLoginSelectedChange() }
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
    val textColor = if (state) IndigoHeading else Color.Gray
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
                        color = IndigoHeading,
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
                            color = IndigoHeading
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
fun Email(
    modifier: Modifier = Modifier,
    error: Boolean,
    Email: String,
    setEmail: (String) -> Unit
) {


    val color1 = Color(0xFFFFFFFF)
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 40.dp)
    ) {

        Box() {
            TextField(
                value = Email,
                onValueChange = { setEmail(it) },
                placeholder = { Text("Enter your Email") },
                leadingIcon = {
                    IconButton(onClick = { }) {
                        Icon(imageVector = Icons.Filled.Email, contentDescription = "Email")
                    }
                },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = color1,
                    cursorColor = Color.Black,
                    focusedIndicatorColor = if (error) Color.Transparent else Color.Red,
                    unfocusedIndicatorColor = if (error) Color.Transparent else Color.Red,

                )


            )


        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Password(modifier: Modifier = Modifier,error:Boolean, Password: String, setPassword: (String) -> Unit) {

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
                onValueChange = { setPassword(it) },
                placeholder = { Text(text = "Enter your Password") },

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
                    focusedIndicatorColor = if (error) Color.Transparent else Color.Red,
                    unfocusedIndicatorColor = if (error) Color.Transparent else Color.Red,
                ),
                visualTransformation = if (Istoggal) androidx.compose.ui.text.input.VisualTransformation.None else androidx.compose.ui.text.input.PasswordVisualTransformation(),
                maxLines = 1


            )


        }
    }
}

@Composable
fun RememberAndForgot(
    modifier: Modifier = Modifier,
    IsChecked: Boolean,
    onCheckedChange: () -> Unit
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth(1f)
    ) {
        Row(horizontalArrangement = Arrangement.SpaceBetween) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(checked = IsChecked, onCheckedChange = { onCheckedChange() })

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

                        color = RebeccaPurpleHilghtText
                    )
                )
            }
        }

    }
}

@Composable
fun ButtonWithCutCornerShape(
    Email: String,
    Passwerd: String,
    LoginAndRigister: Boolean,
    authSignIn: (Email: String, Password: String) -> Unit,
    authSignUp: (Email: String, Password: String) -> Unit
) {

    Button(
        onClick = {
            if (LoginAndRigister) authSignIn(Email, Passwerd) else authSignUp(
                Email,
                Passwerd
            )
        },
        colors = ButtonDefaults.buttonColors(DarkOrchid),
        shape = CutCornerShape(10),
        modifier = Modifier.padding(horizontal = 30.dp)
    ) {
        Text(
            text = if (LoginAndRigister) "Login" else "Register",
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
    Text(text = "or With", modifier = Modifier.fillMaxWidth(1f), textAlign = TextAlign.Center)
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



        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Register(
    modifier: Modifier = Modifier,
    Name: String,
    PhoneNumber: String,
    Address: String,
    setName: (String) -> Unit,
    setPhoneNumber: (String) -> Unit,
    setAddress: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(1f)
            .padding(horizontal = 30.dp),
        horizontalAlignment = CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextField(value = Name,
            onValueChange = {
                setName(it)
            },
            placeholder = { Text("Enter your Name") },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.White,
                cursorColor = Color.Black,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent


            ),
            leadingIcon = {
                IconButton(onClick = { }) {
                    Icon(imageVector = Icons.Filled.AccountCircle, contentDescription = "Email")
                }
            }

        )

        Spacer(modifier = Modifier.height(10.dp))
        TextField(value = PhoneNumber,
            onValueChange = { setPhoneNumber(it) },
            placeholder = { Text("Enter your Phone Number") },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.White,
                cursorColor = Color.Black,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent


            ),
            leadingIcon = {
                IconButton(onClick = { }) {
                    Icon(imageVector = Icons.Filled.Call, contentDescription = "Email")
                }
            }

        )
        Spacer(modifier = Modifier.height(10.dp))
        TextField(value = Address,
            onValueChange = { setAddress(it) },
            placeholder = { Text("Enter your Address") },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.White,
                cursorColor = Color.Black,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent


            ),
            leadingIcon = {
                IconButton(onClick = { }) {
                    Icon(imageVector = Icons.Filled.Home, contentDescription = "Email")
                }
            }

        )
    }
}


@Composable
fun Dropdownroll(selection:(String)->Unit) {
    var selectedOption by remember { mutableStateOf("Select account type") }
    var expanded by remember { mutableStateOf(false) }

    Row(modifier = Modifier.padding(), horizontalArrangement = Arrangement.Absolute.Right) {
        DropdownButton(
            options = listOf("Customer", "Seller"),
            selectedOption = selectedOption,
            onOptionSelected = { option ->
                selectedOption = option
                expanded = false
                selection(selectedOption)
            },
            expanded = expanded,
            onExpandChange = { expanded = it }
        )
    }
}

@Composable
fun DropdownButton(
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit,
    expanded: Boolean,
    onExpandChange: (Boolean) -> Unit
) {
    Button(
        onClick = { onExpandChange(!expanded) },
        modifier = Modifier.padding(vertical = 8.dp),
        colors = ButtonDefaults.run { buttonColors(containerColor = DarkOrchid) }
    ) {
        Text(text = selectedOption, color = Color.White)
    }

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { onExpandChange(false) }
    ) {
        options.forEach { option ->
            DropdownMenuItem(
                text = { Text(option) },
                onClick = { onOptionSelected(option) }
            )
        }
    }
}