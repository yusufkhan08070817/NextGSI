package com.ionexa.nextgsi.Pages


import android.content.Context
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.ionexa.nextgsi.Components.ButtonWithCutCornerShape
import com.ionexa.nextgsi.Components.Combined
import com.ionexa.nextgsi.Components.Dropdownroll
import com.ionexa.nextgsi.Components.Email
import com.ionexa.nextgsi.Components.LoadingScreen
import com.ionexa.nextgsi.Components.LogWithGoogleFacebookApple
import com.ionexa.nextgsi.Components.Login
import com.ionexa.nextgsi.Components.Logn_Register
import com.ionexa.nextgsi.Components.Password
import com.ionexa.nextgsi.Components.Register
import com.ionexa.nextgsi.Components.RememberAndForgot
import com.ionexa.nextgsi.DataClass.Customer
import com.ionexa.nextgsi.DataClass.SignInState
import com.ionexa.nextgsi.FBFireBase.FirebaseAuthManager
import com.ionexa.nextgsi.MVVM.Loginmvvm
import com.ionexa.nextgsi.MVVM.OtpVerificationViewModel
import com.ionexa.nextgsi.R
import com.ionexa.nextgsi.SingleTon.NaveLabels
import com.ionexa.nextgsi.SingleTon.Navigation
import com.ionexa.nextgsi.SingleTon.common
import com.ionexa.nextgsi.SingleTon.common.db
import com.ionexa.nextgsi.SingleTon.common.replaceSpecialChars
import com.ionexa.nextgsi.ui.theme.DarkOrchidwebcolor
import com.ionexa.nextgsi.ui.theme.Standardpurple
import kotlinx.coroutines.delay


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginPage(
    LoginViewModel: Loginmvvm,
    FBauthManager: FirebaseAuthManager,
    navController: NavController,
    state: SignInState,
    OTP: OtpVerificationViewModel,
    onGoogleSignInClick: () -> Unit,

    ) {
    var forgotPassword by remember { mutableStateOf(false) }
    var loadingScreen by remember { mutableStateOf(false) }
    val context = LocalContext.current
    var emailforgot by remember { mutableStateOf("") }
    var corutimescope =rememberCoroutineScope()
    LaunchedEffect(key1 = state.signInError) {
        state.signInError?.let { error ->
            Toast.makeText(context, error, Toast.LENGTH_LONG).show()
        }


    }
    LaunchedEffect(key1 = state.isSignSuccessfull) {
        if (state.isSignSuccessfull) {
            navController.navigate(NaveLabels.Home)
        }
    }
    LaunchedEffect(loadingScreen) {
        // Loop to periodically check email verification status
        while (loadingScreen) {
            delay(1000) // Delay for 1 second

            // Reload the current user
            FBauthManager.getCurrentUser()?.reload()?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val isEmailVerified = FBauthManager.getCurrentUser()?.isEmailVerified == true
                    if (isEmailVerified) {
                        // Navigate to the next screen if email is verified
                        OTP.updatesetdata(
                            Customer(
                                username = LoginViewModel.name,
                                name = LoginViewModel.name,
                                phone = LoginViewModel.phone,
                                email = LoginViewModel.email,
                                address = LoginViewModel.address,
                                password = LoginViewModel.password,
                                role = LoginViewModel.roll,
                                createdAt = common.getCurrentDateTime(),
                                profilePic = common.defaultpic,
                                id = replaceSpecialChars(LoginViewModel.email)
                            )
                        )
                        navController.navigate(NaveLabels.OTPVerificatation)
                        Log.e("TAG", "Email verified")
                        return@addOnCompleteListener
                    } else {
                        Log.e("TAG", "Email not verified")
                    }
                } else {
                    Log.e("TAG", "Failed to reload user")
                }
            }
        }
    }

    val scrollState = rememberScrollState()
    Box(modifier = Modifier.fillMaxSize()) {

        Image(
            painter = painterResource(id = R.drawable.curve),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .offset(50.dp, (0).dp)
                .rotate(-10f),
            contentScale = ContentScale.Crop,
            colorFilter = ColorFilter.tint(Standardpurple)
        )
        Image(
            painter = painterResource(id = R.drawable.curve2),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .offset(0.dp, (0).dp),
            contentScale = ContentScale.Crop,
            colorFilter = ColorFilter.tint(DarkOrchidwebcolor)
        )

        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .fillMaxSize()
                .padding(0.dp, 0.dp, 0.dp, 200.dp) // Add padding for better visual
        ) {
            Login()
            Combined(Modifier)
            Logn_Register(Modifier, LoginViewModel.LoginAndRigister) {
                LoginViewModel.onLoginAndRigister()
            }
            AnimatedVisibility(visible = LoginViewModel.LoginAndRigister) {
                Column {
                    Email(Email = LoginViewModel.email, error = LoginViewModel.emailerror) {
                        LoginViewModel.setemail(it)
                    }
                    Spacer(modifier = Modifier.height(30.dp))
                    Password(
                        Password = LoginViewModel.password, error = LoginViewModel.passworderror
                    ) {
                        LoginViewModel.setpassword(it)
                    }
                    RememberAndForgot(IsChecked = LoginViewModel.remember, forgot = {
                        forgotPassword = true
                    }) {
                        LoginViewModel.onremember()
                    }
                }
            }
            AnimatedVisibility(visible = forgotPassword) {
                Dialog(onDismissRequest = { }, properties = DialogProperties(true,true, usePlatformDefaultWidth = true)) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth(1f).fillMaxHeight(0.5f)
                            .padding(10.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(10.dp)
                        ) {

                            ForgotPasswordScreen(context)

                        }
                    }
                }
            }
            AnimatedVisibility(visible = !LoginViewModel.LoginAndRigister) {
                Column {
                    Register(Name = LoginViewModel.name,
                        PhoneNumber = LoginViewModel.phone,
                        Address = LoginViewModel.address,

                        setName = { name ->
                            LoginViewModel.setname(name)
                        },
                        setPhoneNumber = { phone ->
                            LoginViewModel.setphone(phone)
                        },
                        setAddress = { address ->
                            LoginViewModel.setaddress(address)
                        })
                    Spacer(modifier = Modifier.height(10.dp))
                    Email(Email = LoginViewModel.email, error = LoginViewModel.emailerror) {
                        LoginViewModel.setemail(it)
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Password(
                        Password = LoginViewModel.password, error = LoginViewModel.passworderror
                    ) {
                        LoginViewModel.setpassword(it)
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        modifier = Modifier
                            .padding()
                            .fillMaxWidth(1f),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Dropdownroll() {
                            LoginViewModel.updateroll(it)
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }



            Spacer(modifier = Modifier.height(10.dp))
            ButtonWithCutCornerShape(LoginViewModel.email,
                LoginViewModel.password,
                LoginAndRigister = LoginViewModel.LoginAndRigister,
                authSignIn = { email, password ->
                    if (email.isEmpty()) {
                        LoginViewModel.onemailerror(false)
                        return@ButtonWithCutCornerShape
                    } else {
                        LoginViewModel.onemailerror(true)
                    }
                    if (password.isEmpty()) {
                        LoginViewModel.onpassworderror(false)
                        return@ButtonWithCutCornerShape
                    } else {
                        LoginViewModel.onpassworderror(true)
                    }
                    FBauthManager.signInWithEmail(
                        email = email,
                        password = password,
                        onComplete = { status, user, error ->
                            if (status) {
                                val TAG = "signup"
                                //
                                if (Firebase.auth.currentUser != null)
                                {
                                    if (Firebase.auth.currentUser?.email!=null)
                                    {
                                        val vv= common.replaceSpecialChars(
                                            common.replaceSpecialChars(Firebase.auth.currentUser?.email!!)
                                        )
                                        Log.e("lunnnd",vv)
                                        Toast.makeText(context, vv, Toast.LENGTH_SHORT).show()
                                        val docRef = db.collection("users").document(vv)
                                        docRef.get().addOnSuccessListener { document ->
                                            if (document != null) {
                                                var data = document.data
                                                var profile = data?.get("personel_info") as Map<String, Any>
                                                var roll = profile["role"].toString()
                                                var address=profile["address"].toString()
                                                common.locatation=address
                                                common.roll = roll
                                                if (common.roll == "customer") {
                                                    Navigation.navController.navigate(NaveLabels.Home)
                                                    NaveLabels.DefaultLoag = NaveLabels.Home
                                                }
                                                else {
                                                    if (common.roll == "seller") {
                                                        Navigation.navController.navigate(NaveLabels.seller)
                                                        NaveLabels.DefaultLoag = NaveLabels.seller
                                                    }
                                                    // else NaveLabels.DefaultLoag = NaveLabels.SplashScreen
                                                }
                                                Log.d(TAG, "DocumentSnapshot data: ${profile["role"]}")
                                                Log.e("common.roll", common.roll)
                                            } else {
                                                Log.d(TAG, "No such document")
                                            }
                                        }.addOnFailureListener { exception ->
                                            Log.d(TAG, "get failed with ", exception)
                                        }
                                    }else
                                    {
                                        navController.navigate(NaveLabels.Login)
                                    }


                                }
                                //


                                //

                                val vv = common.replaceSpecialChars(
                                    user?.email ?: common.replaceSpecialChars(LoginViewModel.email)
                                )
                                val docRef = db.collection("users").document(vv)
                                docRef.get().addOnSuccessListener { document ->
                                    document.data?.let { data ->
                                        val profile = data["personel_info"] as? Map<String, Any>
                                        Log.e("profileinfo"," ff${profile.toString()}")
                                        if (profile != null) {
                                            val roll = profile["role"]?.toString()
                                            common.roll = roll ?: ""
                                            val address = profile["address"]?.toString()
                                            common.locatation = address ?: ""

                                            when (common.roll) {
                                                "customer" -> {
                                                    Navigation.navController.navigate(NaveLabels.Home)
                                                    NaveLabels.DefaultLoag = NaveLabels.Home
                                                }
                                                "seller" -> {
                                                    Navigation.navController.navigate(NaveLabels.seller)
                                                    NaveLabels.DefaultLoag = NaveLabels.seller
                                                }
                                                // Add additional roles here if needed
                                                else -> {
                                                    // Handle unexpected roles or fallback
                                                    Log.d(TAG, "Unexpected role: ${common.roll}")
                                                }
                                            }

                                            Log.d(TAG, "DocumentSnapshot data: ${profile["role"]}")
                                            Log.e("common.roll", common.roll)
                                        } else {
                                            Log.d(TAG, "No personel_info or incorrect data format")
                                        }
                                    } ?: run {
                                        Log.d(TAG, "No such document or document data is null")
                                    }
                                }.addOnFailureListener { exception ->
                                    Log.d(TAG, "get failed with ", exception)
                                }

                                //
                            } else {
                                Toast.makeText(context, error, Toast.LENGTH_LONG).show()
                            }
                        })


                },
                authSignUp = { email, password ->
                    if (email.isEmpty()) {
                        // LoginViewModel.onemailerror(true)
                        Toast.makeText(context, "Email is empty", Toast.LENGTH_SHORT).show()
                        return@ButtonWithCutCornerShape
                    } else {
                        LoginViewModel.onemailerror(false)
                    }
                    if (password.isEmpty()) {
                        //   LoginViewModel.onpassworderror(true)
                        Toast.makeText(context, "Password is empty", Toast.LENGTH_SHORT).show()
                        return@ButtonWithCutCornerShape
                    } else {
                        LoginViewModel.onpassworderror(false)
                    }
                    if (LoginViewModel.name.isEmpty()) {

                        return@ButtonWithCutCornerShape
                    }
                    if (LoginViewModel.phone.isEmpty()) {
                        Toast.makeText(context, "Name is empty", Toast.LENGTH_SHORT).show()
                        return@ButtonWithCutCornerShape
                    }
                    if (LoginViewModel.address.isEmpty()) {
                        Toast.makeText(context, "Address is empty", Toast.LENGTH_SHORT).show()
                        return@ButtonWithCutCornerShape
                    }
                    if (LoginViewModel.roll == "Select account type" || LoginViewModel.roll == "") {
                        Toast.makeText(context, "Role is empty", Toast.LENGTH_SHORT).show()
                        return@ButtonWithCutCornerShape
                    } else OTP.phoneNumber.value = "+91" + LoginViewModel.phone
                    if (LoginViewModel.address.isEmpty()) return@ButtonWithCutCornerShape
                    FBauthManager.createAccountWithEmail(
                        email = email,
                        password = password,
                        stateloader = {
                            loadingScreen = it
                        }) { status, user, error ->
                        if (status) {
                            FBauthManager.checkEmailVerification(user = FBauthManager.getCurrentUser(),
                                stateloader = {
                                    loadingScreen = it
                                },
                                onComplete = { status, user, error ->
                                    if (status) {
                                        OTP.phoneNumber.value = "+91" + LoginViewModel.phone
                                        navController.navigate(NaveLabels.OTPVerificatation)
                                    } else {
                                        Toast.makeText(context, error, Toast.LENGTH_LONG).show()
                                    }

                                })
                            navController.navigate(NaveLabels.OTPVerificatation)
                        } else {
                            Toast.makeText(context, error, Toast.LENGTH_LONG).show()
                        }
                    }
                    //  AuthViewMOdel.signUpWithEmail(email, password)

                })/*
            LogWithGoogleFacebookApple(GoogleAuth = {
                // Handle Google login
              //  onGoogleSignInClick()
                println("Google login clicked")
            }, FaceBookAuth = {
                // Handle Facebook login
                println("Facebook login clicked")
            }, AppleAuth = {
                // Handle Apple login
                println("Apple login clicked")
            })
             */

        }
        AnimatedVisibility(visible = loadingScreen) {
            LoadingScreen()
        }
    }
}

@Composable
fun ForgotPasswordScreen(context:Context) {
    var email by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            verifyEmailAndSendPasswordReset(
                context = context,
                email = email,
                onSuccess = {
                    message = "Password reset email sent successfully."
                },
                onFailure = {
                    message = it
                }
            )
        }) {
            Text("Send Password Reset Email")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = message)
    }
}


fun verifyEmailAndSendPasswordReset(
    context: Context, email: String, onSuccess: () -> Unit, onFailure: (String) -> Unit
) {
    if (email.isEmpty()) {
        onFailure("Please enter your email.")
        return
    }

    val auth = FirebaseAuth.getInstance()

    // Verify if the email is registered
    auth.fetchSignInMethodsForEmail(email).addOnCompleteListener { task ->
        if (task.isSuccessful) {
            val signInMethods = task.result?.signInMethods
            if (signInMethods.isNullOrEmpty()) {
                onFailure("Email is not registered.")
            } else {
                // If the email is registered, send password reset email
                auth.sendPasswordResetEmail(email).addOnCompleteListener { resetTask ->
                    if (resetTask.isSuccessful) {
                        onSuccess()
                    } else {
                        resetTask.exception?.let {
                            onFailure(it.message ?: "Failed to send reset email.")
                        }
                    }
                }
            }
        } else {
            task.exception?.let {
                onFailure(it.message ?: "Failed to verify email.")
            }
        }
    }
}


