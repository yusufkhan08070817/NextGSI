package com.ionexa.nextgsi.Pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ionexa.nextgsi.SingleTon.NaveLabels
import com.ionexa.nextgsi.SingleTon.Navigation
import com.ionexa.nextgsi.ui.theme.IndigoHeading
import com.ionexa.nextgsi.ui.theme.Mediumpurple

@Composable
fun AccountPravicy(modifier: Modifier = Modifier) {
    Column (Modifier.fillMaxSize(1f)){
        Card(onClick = { }, modifier = Modifier
            .fillMaxWidth(1f)
            .fillMaxHeight(0.1f), colors = CardDefaults.cardColors(Mediumpurple), shape = CutCornerShape(2.dp), elevation = CardDefaults.cardElevation(12.dp)
        ) {
Column(Modifier.fillMaxSize(1f), verticalArrangement = Arrangement.Bottom) {
    IconButton(onClick = {Navigation.navController.navigate(NaveLabels.Profile) }) {
        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "")
    }
}
Text(text = " We're Sorry to See You Go!")
        }
        Text(
            text = """
     
         We're truly sorry to hear that you're considering deleting your account. Before you go, we want to thank you for being a part of our community. Your experience and feedback mean a lot to us.

         If there's anything that's not working for you, we'd love to know how we can make it better. Your thoughts and suggestions can help us improve and make your experience more enjoyable.

         Would you give us another chance? We’re always here to help if you have any questions or concerns.

         If you still wish to proceed, you can delete your account below.
        """.trimIndent(),
            modifier
                .fillMaxWidth(1f)
                .padding(top = 20.dp))
        Card (modifier = Modifier
            .fillMaxWidth(1f)
            .padding(top = 20.dp), colors = CardDefaults.cardColors(Mediumpurple), shape = CutCornerShape(2.dp), elevation = CardDefaults.cardElevation(12.dp)){
          Column(modifier = Modifier.fillMaxWidth(1f), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
              Text(text = "Do you want to delete your account", color = Color.White)
              Button(onClick = { /*TODO*/ }, colors = ButtonDefaults.buttonColors(Color.White)) {
                  Text(text = "Click hear to delete account", color = IndigoHeading)
              }
          }
        }
    }


}