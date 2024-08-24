package com.ionexa.nextgsi.Pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun Customercare(modifier: Modifier = Modifier) {
    var subject by remember {
        mutableStateOf("")
    }
    var body by remember {
        mutableStateOf("")
    }
   Column(modifier=Modifier.fillMaxSize()) {
       Card(modifier = Modifier
           .fillMaxWidth(1f)
           .fillMaxHeight(0.1f)) {
           Column(modifier = Modifier.fillMaxSize(1f), verticalArrangement = Arrangement.Center,horizontalAlignment = Alignment.CenterHorizontally) {
               Text(text = "Customer care")
           }
       }
       Spacer(modifier = Modifier
           .fillMaxHeight(0.1f)
           .fillMaxWidth(1f))
       TextField(value = subject, onValueChange = {subject=it}, label = { Text(text = "Subject")})
       Spacer(modifier = Modifier
           .fillMaxHeight(0.1f)
           .fillMaxWidth(1f))
       TextField(value = body, onValueChange = {body=it}, label = { Text(text = "Ask here")})

       Button(onClick = { /*TODO*/ }) {
           Text(text = "Submit")
       }

   }
}