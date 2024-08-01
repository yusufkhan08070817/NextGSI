package com.ionexa.nextgsi.MVVM

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class ProfileMVVM:ViewModel() {
    var email by mutableStateOf("Email")
        private set
    var profileImageUrl by mutableStateOf("https://static.vecteezy.com/system/resources/previews/002/002/403/large_2x/man-with-beard-avatar-character-isolated-icon-free-vector.jpg")
        private set
    var password by mutableStateOf("Password")
        private set
    var address by mutableStateOf("Address")
        private set
    var phone by mutableStateOf("Phone")
        private set
    var name by mutableStateOf("Name")
        private set
    fun updateemail(value:String)
    {
        email=value
    }
    fun updateprofileImageUrl(value:String)
    {
        profileImageUrl=value
    }
    fun updatname(value:String)
    {
        name=value
    }
    fun updatepassword(value:String)
    {
        password=value
    }
    fun updateaddress(value:String)
    {
        address=value
    }
    fun updatephone(value:String)
    {
        phone=value
    }
}