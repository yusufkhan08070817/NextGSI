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

        }
        Text(
            text = """
            An About Us page is an integral piece of content to have on your website.

            Every single successful business has one, no matter their industry or what they sell.

            After all, every brand has a story – and your About Us page helps you tell yours.

            That’s important because, these days, story and connection matter more than ever to customers.

            In a Demand Gen survey, 55% of B2B buyers said content that tells a strong, resonating story is what would make them most likely to talk to sales.

            Because your About Us page is one of the first places customers will look to find out about your business and story, it’s a foundational page that deserves time and attention to get right.

            Let’s talk about it, including how to write an About Us page.
        """.trimIndent(),
            modifier
                .fillMaxWidth(1f)
                .padding(top = 20.dp))
        Card (modifier = Modifier.fillMaxWidth(1f).padding(top = 20.dp), colors = CardDefaults.cardColors(Mediumpurple), shape = CutCornerShape(2.dp), elevation = CardDefaults.cardElevation(12.dp)){
          Column(modifier = Modifier.fillMaxWidth(1f), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
              Text(text = "Do you want to delete your account", color = Color.White)
              Button(onClick = { /*TODO*/ }, colors = ButtonDefaults.buttonColors(Color.White)) {
                  Text(text = "Click hear to delete account", color = IndigoHeading)
              }
          }
        }
    }


}