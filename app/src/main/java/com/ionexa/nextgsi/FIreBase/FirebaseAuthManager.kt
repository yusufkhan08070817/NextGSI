package com.ionexa.nextgsi.FIreBase


import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class FirebaseAuthManager {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }

    fun signUpWithEmail(
        email: String,
        password: String,
        onComplete: (Boolean, FirebaseUser?, String?) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onComplete(true, auth.currentUser, null)
                } else {
                    onComplete(false, null, task.exception?.message)
                }
            }
    }

    fun signInWithEmail(
        email: String,
        password: String,
        onComplete: (Boolean, FirebaseUser?, String?) -> Unit
    ) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onComplete(true, auth.currentUser, null)
                } else {
                    onComplete(false, null, task.exception?.message)
                }
            }
    }

    fun signOut() {
        auth.signOut()
    }

    fun sendPasswordResetEmail(
        email: String,
        onComplete: (Boolean, String?) -> Unit
    ) {
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onComplete(true, null)
                } else {
                    onComplete(false, task.exception?.message)
                }
            }
    }
}