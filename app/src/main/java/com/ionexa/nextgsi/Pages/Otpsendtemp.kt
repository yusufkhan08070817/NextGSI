package com.ionexa.nextgsi.Pages

import android.app.Activity
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ionexa.nextgsi.MVVM.OtpVerificationViewModel

@Composable
fun AuthScreen(activity: Activity, viewModel: OtpVerificationViewModel = viewModel()) {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = viewModel.phoneNumber.value,
            onValueChange = { viewModel.phoneNumber.value = it },
            label = { Text("Phone Number") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { viewModel.startPhoneNumberVerification(activity) }) {
            Text("Send OTP")
        }
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = viewModel.otpCode.value,
            onValueChange = { viewModel.otpCode.value = it },
            label = { Text("OTP Code") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { viewModel.verifyPhoneNumberWithCode() }) {
            Text("Verify OTP")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = viewModel.verificationState.value)
    }
}
