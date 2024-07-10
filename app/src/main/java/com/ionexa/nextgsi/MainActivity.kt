package com.ionexa.nextgsi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ionexa.nextgsi.Components.*
import com.ionexa.nextgsi.MVVM.AuthViewModel
import com.ionexa.nextgsi.MVVM.Loginmvvm
import com.ionexa.nextgsi.ui.theme.NextGsiTheme

class MainActivity : ComponentActivity() {
    private val LoginViewModel by viewModels<Loginmvvm>()
    private val AuthViewMOdel by viewModels<AuthViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NextGsiTheme {
                val scrollState = rememberScrollState()
                Box(modifier = Modifier.fillMaxSize()) {
                    Image(
                        painter = painterResource(id = R.drawable.curve),
                        contentDescription = null,
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .fillMaxWidth()
                            .offset(50.dp, (-10).dp)
                            .rotate(-10f),
                        contentScale = ContentScale.Crop
                    )
                    Image(
                        painter = painterResource(id = R.drawable.curve2),
                        contentDescription = null,
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .fillMaxWidth()
                            .offset(0.dp, (-10).dp),
                        contentScale = ContentScale.Crop
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
                        if (LoginViewModel.LoginAndRigister) {
                            Email(Email = LoginViewModel.email,error=LoginViewModel.emailerror) {
                                LoginViewModel.setemail(it)
                            }
                            Spacer(modifier = Modifier.height(30.dp))
                            Password(Password = LoginViewModel.password,error=LoginViewModel.passworderror) {
                                LoginViewModel.setpassword(it)
                            }
                            RememberAndForgot(IsChecked = LoginViewModel.remember) {
                                LoginViewModel.onremember()
                            }
                        } else {
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
                            Email(Email = LoginViewModel.email,error=LoginViewModel.emailerror) {
                                LoginViewModel.setemail(it)
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                            Password(Password = LoginViewModel.password, error = LoginViewModel.passworderror) {
                                LoginViewModel.setpassword(it)
                            }
                        }


                        Spacer(modifier = Modifier.height(10.dp))
                        ButtonWithCutCornerShape(LoginViewModel.email,
                            LoginViewModel.password,
                            LoginAndRigister = LoginViewModel.LoginAndRigister,
                            authSignIn = { email, password ->
                               if (email.isEmpty())
                               {
                                   LoginViewModel.onemailerror(false)
                                   return@ButtonWithCutCornerShape
                               }else{
                                   LoginViewModel.onemailerror(true)
                               }
                                if (password.isEmpty())
                                {
                                    LoginViewModel.onpassworderror(false)
                                    return@ButtonWithCutCornerShape
                                }else{
                                    LoginViewModel.onpassworderror(true)
                                }
                                AuthViewMOdel.signInWithEmail(email, password)

                            },
                            authSignUp = { email, password ->
                                if (email.isEmpty())
                                {
                                    LoginViewModel.onemailerror(true)
                                    return@ButtonWithCutCornerShape
                                }else{
                                    LoginViewModel.onemailerror(false)
                                }
                                if (password.isEmpty())
                                {
                                    LoginViewModel.onpassworderror(true)
                                    return@ButtonWithCutCornerShape
                                }else{
                                    LoginViewModel.onpassworderror(false)
                                }
                                AuthViewMOdel.signUpWithEmail(email, password)

                            })
                        LogWithGoogleFacebookApple(GoogleAuth = {
                            // Handle Google login
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
        }
    }
}
