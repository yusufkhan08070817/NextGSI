package com.ionexa.nextgsi.MVVM

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.ionexa.nextgsi.DataClass.GoogleSignINResult
import com.ionexa.nextgsi.DataClass.SignInState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SignInMVVM:ViewModel()  {
    private val _state= MutableStateFlow (SignInState())
    val state= _state.asStateFlow()
    fun onSignInResult(result: GoogleSignINResult){
        _state.value=state.value.copy(
            isSignSuccessfull = result.data!=null,
            signInError = result.errormsg
        )

    }
    fun resetState() {
        _state.update { SignInState() }

    }
}