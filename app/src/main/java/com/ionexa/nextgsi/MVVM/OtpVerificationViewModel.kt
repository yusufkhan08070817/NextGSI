package com.ionexa.nextgsi.MVVM

import android.app.Activity
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ionexa.nextgsi.DataClass.Customer
import java.util.concurrent.TimeUnit

class OtpVerificationViewModel : ViewModel() {
    private val auth: FirebaseAuth = Firebase.auth
    var phoneNumber = mutableStateOf("")
    val otpCode = mutableStateOf("")
    val verificationId = mutableStateOf("")
    val verificationState = mutableStateOf("")
    var setdata = mutableStateOf<Customer>(Customer("","","","","","","","","",""))
        private set
    fun updatesetdata(newStatus: Customer) {
        setdata.value = newStatus
    }
var status= mutableStateOf(false)
    private set
   fun updateStatus(newStatus: Boolean) {
       status.value = newStatus
   }
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken

    private val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            signInWithPhoneAuthCredential(credential)
        }

        override fun onVerificationFailed(e: FirebaseException) {
            verificationState.value = "Verification failed: ${e.message}"
        }

        override fun onCodeSent(id: String, token: PhoneAuthProvider.ForceResendingToken) {
            verificationId.value = id
            resendToken = token
            verificationState.value = "OTP sent"
        }
    }

    fun startPhoneNumberVerification(activity: Activity) {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber.value)
            .setTimeout(120L, TimeUnit.SECONDS)
            .setActivity(activity)
            .setCallbacks(callbacks)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    fun verifyPhoneNumberWithCode() {
        val credential = PhoneAuthProvider.getCredential(verificationId.value, otpCode.value)
        signInWithPhoneAuthCredential(credential)
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential).addOnCompleteListener { task ->

            if (task.isSuccessful) {
                verificationState.value = "Sign-in successful"
                updateStatus(true)
            } else {
                verificationState.value = "Sign-in failed: ${task.exception?.message}"
                updateStatus(false)
            }
        }
    }
}
