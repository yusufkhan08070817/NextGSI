package com.ionexa.nextgsi.Pages

import android.content.Context
import android.content.Intent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import com.ionexa.nextgsi.R
import com.ionexa.nextgsi.SingleTon.NaveLabels
import com.ionexa.nextgsi.SingleTon.Navigation
import com.ionexa.nextgsi.ui.theme.Lavender
import com.ionexa.nextgsi.ui.theme.RebeccaPurpleHilghtText


@Composable
fun HeamBurger(hemState:()->Unit,state:Boolean,logout:()->Unit) {
    val context = LocalContext.current
val stateScroll = rememberScrollState()
    AnimatedVisibility(visible = state, enter = slideInHorizontally() + fadeIn(), exit = slideOutHorizontally() + fadeOut()) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .fillMaxHeight(1f)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .fillMaxHeight(1f),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .padding(top = 20.dp), horizontalArrangement = Arrangement.Absolute.Right
                ) {

                    IconButton(onClick = { hemState() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.cross),
                            contentDescription = "HeamBurger",
                            modifier = Modifier
                                .padding(10.dp)
                                .width(80.dp)
                                .height(80.dp)
                        )

                    }



                }
                Column(modifier = Modifier
                    .fillMaxWidth(1f)
                    .fillMaxHeight(1f)
                    .padding(bottom = 0.dp)
                    .verticalScroll(stateScroll)
                ) {
                    TextCard(labletext = "Your Order", icon = R.drawable.order){Navigation.navController.navigate(NaveLabels.CartHistory)}
                    TextCard(labletext = "Address Book", icon = R.drawable.addressbook){}
                    TextCard(labletext = "Collected coupons", icon = R.drawable.cupons){}
                    TextCard(labletext = "Shear this app", icon = R.drawable.shear){shareContent(context, "https://play.google.com/store/search?q=blinkit&c=apps&hl=en_IN \n hey download this app")}

                    TextCard(labletext = "About us", icon = R.drawable.info){Navigation.navController.navigate(NaveLabels.AboutUs)}
                    TextCard(labletext = "Get Feeding Indian receipt", icon = R.drawable.receipt){}
                    TextCard(labletext = "Account Privacy", icon = R.drawable.privacy){Navigation.navController.navigate(NaveLabels.AccountPravicy)}
                    TextCard(labletext = "Notification Prefernces", icon = R.drawable.noti){}
                    TextCard(labletext = "Customer Care ", icon = R.drawable.customercare){}
                    TextCard(labletext = "Log Out", icon = R.drawable.baseline_logout_24){logout()}
                }
            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextCard(
    modifier: Modifier = Modifier,
    labletext: String,
    icon: Int,
    onItemClick: () -> Unit
) {
    Card(
        onClick = { onItemClick() },
        modifier = modifier
            .padding(4.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Lavender),
        elevation = CardDefaults.cardElevation(5.dp)
    ) {
        Box {

            TextField(
                value = labletext,
                onValueChange = { /* No-op, as the TextField is read-only */ },
                readOnly = true,
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    cursorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
                leadingIcon = {
                    Image(
                        painter = painterResource(id = icon),
                        contentDescription = null,
                        modifier = Modifier
                            .height(20.dp)
                            .width(20.dp)
                            .clickable { onItemClick() },
                        colorFilter = ColorFilter.tint(RebeccaPurpleHilghtText)
                    )
                }
            )

        }
    }
}

private fun shareContent(context: Context, content: String) {

    val shareIntent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, content)
        type = "text/plain"
    }
    context.startActivity(Intent.createChooser(shareIntent, "Share via"))
}
