package com.ionexa.nextgsi.Pages


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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ionexa.nextgsi.Components.ButtonWithCutCornerShape
import com.ionexa.nextgsi.Components.Combined
import com.ionexa.nextgsi.Components.Dropdownroll
import com.ionexa.nextgsi.Components.Email
import com.ionexa.nextgsi.Components.LogWithGoogleFacebookApple
import com.ionexa.nextgsi.Components.Login
import com.ionexa.nextgsi.Components.Logn_Register
import com.ionexa.nextgsi.Components.Password
import com.ionexa.nextgsi.Components.Register
import com.ionexa.nextgsi.Components.RememberAndForgot
import com.ionexa.nextgsi.MVVM.Loginmvvm
import com.ionexa.nextgsi.R
import com.ionexa.nextgsi.SingleTon.NaveLabels
import com.ionexa.nextgsi.ui.theme.DarkOrchidwebcolor
import com.ionexa.nextgsi.ui.theme.Standardpurple

@Composable
fun LoginPage( LoginViewModel: Loginmvvm,navController: NavController) {

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
            Logn_Register( Modifier,LoginViewModel.LoginAndRigister) {
                LoginViewModel.onLoginAndRigister()
            }
            AnimatedVisibility(visible = LoginViewModel.LoginAndRigister) {
               Column {
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
                   Email(Email = LoginViewModel.email,error=LoginViewModel.emailerror) {
                       LoginViewModel.setemail(it)
                   }
                   Spacer(modifier = Modifier.height(10.dp))
                   Password(Password = LoginViewModel.password, error = LoginViewModel.passworderror) {
                       LoginViewModel.setpassword(it)
                   }
                   Spacer(modifier = Modifier.height(10.dp))
                   Row(modifier = Modifier
                       .padding()
                       .fillMaxWidth(1f), horizontalArrangement = Arrangement.Center) {
                       Dropdownroll()
                   }
                   Spacer(modifier = Modifier.height(10.dp))
               }
            }
            if (LoginViewModel.LoginAndRigister) {

            } else {

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
                    //AuthViewMOdel.signInWithEmail(email, password)
                    navController.navigate(NaveLabels.Home)

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
                  //  AuthViewMOdel.signUpWithEmail(email, password)

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