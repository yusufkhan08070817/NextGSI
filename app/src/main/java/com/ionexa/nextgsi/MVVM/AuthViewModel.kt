package com.ionexa.nextgsi.MVVM

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import com.ionexa.nextgsi.FIreBase.FirebaseAuthManager


class AuthViewModel : ViewModel() {
    private val authManager = FirebaseAuthManager()

    private val _currentUser = MutableLiveData<FirebaseUser?>()
    val currentUser: LiveData<FirebaseUser?> = _currentUser

    private val _authResult = MutableLiveData<Result<FirebaseUser?>>()
    val authResult: LiveData<Result<FirebaseUser?>> = _authResult

    private val _passwordResetResult = MutableLiveData<Result<Unit>>()
    val passwordResetResult: LiveData<Result<Unit>> = _passwordResetResult

    init {
        _currentUser.value = authManager.getCurrentUser()
    }

    fun signUpWithEmail(email: String, password: String) {
        authManager.signUpWithEmail(email, password) { success, user, error ->
            if (success) {
                _currentUser.value = user
                _authResult.value = Result.success(user)
            } else {
                _authResult.value = Result.failure(Exception(error))
            }
        }
    }

    fun signInWithEmail(email: String, password: String) {
        authManager.signInWithEmail(email, password) { success, user, error ->
            if (success) {
                _currentUser.value = user
                _authResult.value = Result.success(user)
            } else {
                _authResult.value = Result.failure(Exception(error))
            }
        }
    }

    fun signOut() {
        authManager.signOut()
        _currentUser.value = null
    }

    fun sendPasswordResetEmail(email: String) {
        authManager.sendPasswordResetEmail(email) { success, error ->
            if (success) {
                _passwordResetResult.value = Result.success(Unit)
            } else {
                _passwordResetResult.value = Result.failure(Exception(error))
            }
        }
    }
}