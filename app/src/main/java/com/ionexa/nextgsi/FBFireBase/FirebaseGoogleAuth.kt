package com.ionexa.nextgsi.FBFireBase

import android.content.Context
import android.content.Intent
import android.content.IntentSender
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient

import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ionexa.nextgsi.DataClass.GoogleSignINResult
import com.ionexa.nextgsi.DataClass.GoogleUserData
import com.ionexa.nextgsi.R
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.tasks.await

class FirebaseGoogleAuth(
    private val context: Context,
    private val oneTapClient: SignInClient
) {
    private val auth = Firebase.auth
    suspend fun signinwithgoogle():IntentSender {
      val result=  try {
            oneTapClient.beginSignIn(buildSignInRequest())
                .await()
        } catch (E: Exception) {
            E.printStackTrace()
            if (E is CancellationException) throw E
            null
        }
return  result?.pendingIntent?.intentSender!!
    }
    suspend fun getSignInResultFromIntebt(intent:Intent):GoogleSignINResult{
        val credential=oneTapClient.getSignInCredentialFromIntent(intent)
        val googleIdTocken=credential.googleIdToken
        val googleCredentials= GoogleAuthProvider.getCredential(googleIdTocken,null)
        return try {
           val user= auth.signInWithCredential(googleCredentials).await().user
           GoogleSignINResult(
               data = user?.run {
                   GoogleUserData(
                       userId = uid,
                       userName = displayName,
                       profilePictureUrl = photoUrl?.toString()
                   )
               }, errormsg = null
           )
        }catch (E:Exception)
        {
            E.printStackTrace()
            if (E is CancellationException) throw E
          GoogleSignINResult(data = null, errormsg = E.message)
        }
    }
suspend fun signOut(){
    try {
        oneTapClient.signOut().await()
        auth.signOut()
    }catch (E:Exception){
        if (E is CancellationException) throw E
    }


}
    fun getSignedInUser():GoogleUserData? = auth.currentUser?.run {
        GoogleUserData(
            userId = uid,
            userName = displayName,
            profilePictureUrl = photoUrl?.toString()
        )
    }

    private fun buildSignInRequest(): BeginSignInRequest {
        val beginSignInRequest = BeginSignInRequest.Builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setFilterByAuthorizedAccounts(false)
                    .setServerClientId(context.getString(R.string.default_web_client_id))
                    .build()
            )
            .setAutoSelectEnabled(true)
            .build()
        return beginSignInRequest
    }


}