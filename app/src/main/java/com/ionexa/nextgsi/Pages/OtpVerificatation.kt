package com.ionexa.nextgsi.Pages

import android.app.Activity
import android.os.Build
import android.os.CountDownTimer
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.Navigation
import com.ionexa.nextgsi.DataClass.Customer
import com.ionexa.nextgsi.FBFireBase.FSDB
import com.ionexa.nextgsi.FBFireBase.FireBaseStoreDBMyClass
import com.ionexa.nextgsi.MVVM.Loginmvvm
import com.ionexa.nextgsi.MVVM.OtpVerificationViewModel


import com.ionexa.nextgsi.SingleTon.NaveLabels
import com.ionexa.nextgsi.SingleTon.Routes
import com.ionexa.nextgsi.SingleTon.common
import com.ionexa.nextgsi.ui.theme.RebeccaPurpleHilghtText
import kotlinx.coroutines.launch
import java.util.UUID


@Composable
fun OtpVerification(
    modifier: Modifier = Modifier,
    activity: Activity,
    loginViewModel: Loginmvvm,
    viewModel: OtpVerificationViewModel = viewModel()
) {
    var otpText1 by remember { mutableStateOf("") }
    var otpText2 by remember { mutableStateOf("") }
    var otpText3 by remember { mutableStateOf("") }
    var otpText4 by remember { mutableStateOf("") }
    var otpText5 by remember { mutableStateOf("") }
    var otpText6 by remember { mutableStateOf("") }

    val focusRequester1 = remember { FocusRequester() }
    val focusRequester2 = remember { FocusRequester() }
    val focusRequester3 = remember { FocusRequester() }
    val focusRequester4 = remember { FocusRequester() }
    val focusRequester5 = remember { FocusRequester() }
    val focusRequester6 = remember { FocusRequester() }

    val focusManager = LocalFocusManager.current
    val context = LocalContext.current
    val fbcorutinscope = rememberCoroutineScope()
    val fsdb = FSDB()
    LaunchedEffect(Unit) {
        viewModel.startPhoneNumberVerification(activity)

    }
    LaunchedEffect(viewModel.status.value) {
        if (viewModel.status.value) {
            val customerdata = Customer(
                id = viewModel.setdata.value.id.lowercase(),
                name = loginViewModel.name.lowercase(),
                email = common.encodeToBase64(loginViewModel.email.lowercase()),
                phone = loginViewModel.phone.lowercase(),
                password = common.encodeToBase64(loginViewModel.password.lowercase()),
                role = loginViewModel.roll.lowercase(),
                address = loginViewModel.address.lowercase(),
                profilePic = common.encodeToBase64(common.defaultpic.lowercase()),
                username = loginViewModel.name.lowercase(),
                createdAt = common.getCurrentDateTime().lowercase()
            )

            val customerhashmap = fsdb.run { customerdata.toHashMap() }
            val uploadhashmap= hashMapOf<String,Any?>()
            uploadhashmap.put("personel_info",customerhashmap)
            fsdb.uploadDataToFireStoreDB(uploadhashmap, "users", customerdata.id, onsuccess = {
                com.ionexa.nextgsi.SingleTon.Navigation.navController.navigate(NaveLabels.Home)
            })
            {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            }

        }
    }
    Column {
        Column(
            modifier = Modifier
                .fillMaxWidth(1f)
                .fillMaxHeight(0.1f)
                .background(RebeccaPurpleHilghtText),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Phone Number Verification :${viewModel.phoneNumber.value} ",
                modifier = Modifier.padding(10.dp),
                color = Color.White,
                fontSize = 15.sp
            )
        }
        Card(
            modifier
                .fillMaxWidth(1f)
                .padding(10.dp)
        ) {
            Text(text = viewModel.verificationState.value)
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize(1f)
            .padding(10.dp), contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(1f)
                .fillMaxHeight(0.4f)
                .padding(10.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.elevatedCardElevation(12.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .padding(10.dp)
            ) {

                Text(
                    text = "Verify your phone number",
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = RebeccaPurpleHilghtText
                    )
                )
                Text(
                    text = "Don't share your OTP with others",
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontSize = 16.sp, fontWeight = FontWeight.Normal, color = Color.Gray
                    )
                )
            }
            TimerScreen()
            Box(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .fillMaxHeight(0.5f),
                contentAlignment = Alignment.Center
            ) {
                Row() {
                    OtpTextField(
                        value = otpText1, onValueChange = {
                            otpText1 = it
                            if (otpText1.length == 1) focusRequester2.requestFocus()
                        }, focusRequester = focusRequester1
                    )
                    OtpTextField(
                        value = otpText2, onValueChange = {
                            otpText2 = it
                            if (otpText2.length == 1) focusRequester3.requestFocus()
                            else if (otpText2.isEmpty()) focusRequester1.requestFocus()
                        }, focusRequester = focusRequester2
                    )
                    OtpTextField(
                        value = otpText3, onValueChange = {
                            otpText3 = it
                            if (otpText3.length == 1) focusRequester4.requestFocus()
                            else if (otpText3.isEmpty()) focusRequester2.requestFocus()
                        }, focusRequester = focusRequester3
                    )
                    OtpTextField(
                        value = otpText4, onValueChange = {
                            otpText4 = it
                            if (otpText4.length == 1) focusRequester5.requestFocus()
                            else if (otpText4.isEmpty()) focusRequester3.requestFocus()
                        }, focusRequester = focusRequester4
                    )
                    OtpTextField(
                        value = otpText5, onValueChange = {
                            otpText5 = it
                            if (otpText5.length == 1) focusRequester6.requestFocus()
                            else if (otpText5.isEmpty()) focusRequester4.requestFocus()
                        }, focusRequester = focusRequester5
                    )
                    OtpTextField(
                        value = otpText6, onValueChange = {
                            otpText6 = it
                            if (otpText6.isEmpty()) focusRequester5.requestFocus()
                        }, focusRequester = focusRequester6
                    )
                }

            }
            Spacer(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .fillMaxHeight(0.2f)
            )
            Row(
                Modifier.fillMaxWidth(1f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(0.7f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Button(onClick = { /*TODO*/ }) {
                        Text(text = "Resend")
                    }
                    Button(onClick = {
                        viewModel.otpCode.value =
                            otpText1 + otpText2 + otpText3 + otpText4 + otpText5 + otpText6
                        viewModel.verifyPhoneNumberWithCode()

                    }) {
                        Text(text = "Verify")
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OtpTextField(
    value: String,
    onValueChange: (String) -> Unit,
    focusRequester: FocusRequester,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = Modifier
            .height(70.dp)
            .width(50.dp)
            .padding(5.dp)
            .border(3.dp, color = Color.White)
            .border(5.dp, color = RebeccaPurpleHilghtText),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        TextField(
            value = value,
            onValueChange = onValueChange,
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent,
                cursorColor = Color.Black,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
            ),
            modifier = Modifier
                .fillMaxWidth(1f)
                .focusRequester(focusRequester)
        )
    }
}

@Composable
fun TimerScreen() {
    val context = LocalContext.current
    val timerValue = remember { mutableStateOf("03:00") }
    val isRunning = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        val timer = createCountDownTimer(timerValue, context, isRunning)
        timer.start()
        isRunning.value = true
    }

    Box(
        modifier = Modifier
            .fillMaxWidth(0.2f)
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = timerValue.value,

            )
    }
}

fun createCountDownTimer(
    timerValue: MutableState<String>,
    context: android.content.Context,
    isRunning: MutableState<Boolean>
): CountDownTimer {
    return object : CountDownTimer(180000, 1000) {
        override fun onTick(millisUntilFinished: Long) {
            val minutes = (millisUntilFinished / 1000) / 60
            val seconds = (millisUntilFinished / 1000) % 60
            timerValue.value = String.format("%02d:%02d", minutes, seconds)
        }

        override fun onFinish() {
            timerValue.value = "00:00"
            Toast.makeText(context, "Time out", Toast.LENGTH_SHORT).show()
            isRunning.value = false
        }
    }
}

