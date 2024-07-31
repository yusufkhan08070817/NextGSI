package com.ionexa.nextgsi.Pages

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ionexa.nextgsi.R
import com.ionexa.nextgsi.ui.theme.Lavender
import com.ionexa.nextgsi.ui.theme.RebeccaPurpleHilghtText


@Composable
fun HeamBurger(hemState:()->Unit,state:Boolean) {
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
                    TextCard(labletext = "Your Order", icon = R.drawable.order){}
                    TextCard(labletext = "Address Book", icon = R.drawable.addressbook){}
                    TextCard(labletext = "Collected coupons", icon = R.drawable.cupons){}
                    TextCard(labletext = "Shear this app", icon = R.drawable.shear){}
                    TextCard(labletext = "About us", icon = R.drawable.info){}
                    TextCard(labletext = "Get Feeding Indian receipt", icon = R.drawable.receipt){}
                    TextCard(labletext = "Account Privacy", icon = R.drawable.privacy){}
                    TextCard(labletext = "Notification Prefernces", icon = R.drawable.noti){}
                    TextCard(labletext = "Log Out", icon = R.drawable.baseline_logout_24){}
                }
            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextCard(modifier: Modifier = Modifier,
             labletext:String,

    icon:Int,
             onitemclick:()->Unit) {
   Card(onClick = { onitemclick()}, modifier = modifier
       .padding(4.dp)
       .fillMaxWidth(1f)
   ,
       colors = CardDefaults.cardColors(containerColor = Lavender),
       elevation = CardDefaults.cardElevation(5.dp)
   ) {


       TextField(value =labletext , onValueChange = {labletext}, readOnly = true, colors = TextFieldDefaults.textFieldColors(
           containerColor = Color.Transparent,
           cursorColor = Color.Transparent,
           focusedIndicatorColor = Color.Transparent,
           unfocusedIndicatorColor = Color.Transparent
       )
       ,
           leadingIcon = {
               Image(painter = painterResource(id = icon), contentDescription = null, modifier = Modifier
                   .height(20.dp)
                   .width(20.dp), colorFilter = ColorFilter.tint(RebeccaPurpleHilghtText)

               )
           }
       )

   }
}