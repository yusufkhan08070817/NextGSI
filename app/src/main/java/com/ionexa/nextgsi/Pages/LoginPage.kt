package com.ionexa.nextgsi.Pages


import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.navigation.NavController
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
import com.ionexa.nextgsi.DataClass.SignInState
import com.ionexa.nextgsi.FIreBase.FirebaseAuthManager
import com.ionexa.nextgsi.MVVM.Loginmvvm
import com.ionexa.nextgsi.R
import com.ionexa.nextgsi.SingleTon.NaveLabels
import com.ionexa.nextgsi.ui.theme.DarkOrchidwebcolor
import com.ionexa.nextgsi.ui.theme.Standardpurple
import kotlinx.coroutines.delay

@Composable
fun LoginPage(
    LoginViewModel: Loginmvvm,
    FBauthManager: FirebaseAuthManager,
    navController: NavController,
    state: SignInState,
    onGoogleSignInClick: () -> Unit
) {
    var loadingScreen by remember { mutableStateOf(false) }
    val context = LocalContext.current
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
            contentScale = ContentScale.Crop, colorFilter = ColorFilter.tint(Standardpurple)
        )
        Image(
            painter = painterResource(id = R.drawable.curve2),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .offset(0.dp, (0).dp),
            contentScale = ContentScale.Crop, colorFilter = ColorFilter.tint(DarkOrchidwebcolor)
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
                        Password = LoginViewModel.password,
                        error = LoginViewModel.passworderror
                    ) {
                        LoginViewModel.setpassword(it)
                    }
                    RememberAndForgot(IsChecked = LoginViewModel.remember) {
                        LoginViewModel.onremember()
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
                        Password = LoginViewModel.password,
                        error = LoginViewModel.passworderror
                    ) {
                        LoginViewModel.setpassword(it)
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        modifier = Modifier
                            .padding()
                            .fillMaxWidth(1f), horizontalArrangement = Arrangement.Center
                    ) {
                        Dropdownroll()
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
            if (LoginViewModel.LoginAndRigister) {

            } else {

            }

            AnimatedVisibility(visible = loadingScreen) {
                LoadingScreen()
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
                      email=email,
                      password=password, onComplete = {
                          status, user, error ->
                          if (status) {
                              navController.navigate(NaveLabels.Home)
                          } else {
                              Toast.makeText(context, error, Toast.LENGTH_LONG).show()
                          }
                      }
                  )


                },
                authSignUp = { email, password ->
                    if (email.isEmpty()) {
                        LoginViewModel.onemailerror(true)
                        return@ButtonWithCutCornerShape
                    } else {
                        LoginViewModel.onemailerror(false)
                    }
                    if (password.isEmpty()) {
                        LoginViewModel.onpassworderror(true)
                        return@ButtonWithCutCornerShape
                    } else {
                        LoginViewModel.onpassworderror(false)
                    }
                    FBauthManager.createAccountWithEmail(
                        email = email,
                        password = password,
                        stateloader = {
loadingScreen=it
                        }
                    )
                    { status, user, error ->
                        if (status) {
                            FBauthManager.checkEmailVerification(
                                user=FBauthManager.getCurrentUser(),
                                stateloader = {
                                    loadingScreen = it
                                }, onComplete = {
                                    status, user, error ->
                                    if (status) {
                                        navController.navigate(NaveLabels.Home)
                                    } else {
                                        Toast.makeText(context, error, Toast.LENGTH_LONG).show()}

                                }
                            )
                            navController.navigate(NaveLabels.OTPVerificatation)
                        } else {
                            Toast.makeText(context, error, Toast.LENGTH_LONG).show()
                        }
                    }
                    //  AuthViewMOdel.signUpWithEmail(email, password)

                })
            LogWithGoogleFacebookApple(GoogleAuth = {
                // Handle Google login
                onGoogleSignInClick()
                println("Google login clicked")
            }, FaceBookAuth = {
                // Handle Facebook login
                println("Facebook login clicked")
            }, AppleAuth = {
                // Handle Apple login
                println("Apple login clicked")
            })

        }
    }
}