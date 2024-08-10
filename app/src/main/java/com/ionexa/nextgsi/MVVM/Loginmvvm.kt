package com.ionexa.nextgsi.MVVM

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class Loginmvvm : ViewModel() {
    var LoginAndRigister by mutableStateOf(true)
        private set

    fun onLoginAndRigister() {
        LoginAndRigister = !LoginAndRigister
    }

    var email by mutableStateOf("")
        private set

    fun setemail(newEmail: String) {
        email = newEmail
    }

    var password by mutableStateOf("")
        private set

    fun setpassword(newPassword: String) {
        password = newPassword
    }

    var remember by mutableStateOf(false)
        private set

    fun onremember() {
        remember = !remember
    }
    var name by mutableStateOf("")
        private set
    fun setname(newname: String) {
        name = newname
    }

    var phone by mutableStateOf("")
        private set
    fun setphone(newphone: String) {
        phone = newphone
    }
    var address by mutableStateOf("")
        private set
    fun setaddress(newaddress: String) {
        address = newaddress
    }
 var passworderror by mutableStateOf(true)
     private set
    fun onpassworderror(state: Boolean) {
        passworderror = state
    }

    var emailerror by mutableStateOf(true)
        private set
    fun onemailerror(state: Boolean) {
        emailerror = state
    }
    var roll by mutableStateOf("")
        private set
    fun updateroll(newroll: String) {
        roll=newroll
    }


}