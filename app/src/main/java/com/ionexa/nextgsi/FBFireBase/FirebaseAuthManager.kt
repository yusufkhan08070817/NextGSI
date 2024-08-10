package com.ionexa.nextgsi.FBFireBase


import android.os.Handler
import android.os.Looper
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.ionexa.nextgsi.MainActivity.Companion.FBAuth

class FirebaseAuthManager {

   val auth=FBAuth

    fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }

    fun createAccountWithEmail(
        email: String,
        password: String,stateloader:(Boolean)->Unit,
        onComplete: (Boolean, FirebaseUser?, String?) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                auth
                if (task.isSuccessful) {
                    stateloader(true)
                  auth.currentUser?.sendEmailVerification()?.addOnSuccessListener {
                     val verificatation= auth.currentUser?.isEmailVerified
                      if (verificatation==true)
                      {
                          onComplete(true, auth.currentUser, null)
                      }
                      else{
                          onComplete(false, null, "Email not verified")
                      }
                  }?.addOnFailureListener {
                      onComplete(false, null, it.message)
                  }

                } else {
                    stateloader(false)
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
var verificatation=auth.currentUser?.isEmailVerified

                   if (verificatation==true)
                   {
                       onComplete(true, auth.currentUser, null)
                   }else
                   {
                       onComplete(false, null, "Email not verified")
                   }
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
    fun deleteUserAccount(onComplete: (Boolean, String?) -> Unit) {
        val user = FirebaseAuth.getInstance().currentUser
        user?.delete()?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                onComplete(true, null)
            } else {
                onComplete(false, task.exception?.message)
            }
        }
    }
    fun checkEmailVerification(
        user: FirebaseUser?=auth.currentUser,
        stateloader: (Boolean) -> Unit,
        onComplete: (Boolean, FirebaseUser?, String?) -> Unit
    ) {
        val handler = Handler(Looper.getMainLooper())
        val runnable = object : Runnable {
            override fun run() {
                auth.currentUser?.reload()?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        if (user!!.isEmailVerified) {
                            onComplete(true, user, null)
                        } else {
                            // Keep checking every few seconds
                            Log.e("TAG", "Email not verified")
                            handler.postDelayed(this, 1000) // Check every 3 seconds
                        }
                    } else {
                        stateloader(false)
                        onComplete(false, null, task.exception?.message)
                    }
                }
            }
        }
        handler.post(runnable)
    }
}