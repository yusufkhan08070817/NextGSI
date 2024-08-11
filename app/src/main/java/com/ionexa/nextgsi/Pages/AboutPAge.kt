package com.ionexa.nextgsi.Pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
fun AboutUs(modifier: Modifier = Modifier) {
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
           NEXT is at the forefront of the Q-commerce revolution, transforming the way people
           experience e-commerce. Our mission is to elevate the convenience of online shopping by
           reducing delivery times from days to minutes, bringing the future of retail to your doorstep.
           As a pioneering Q-commerce startup, we leverage cutting-edge technology, including
           artificial intelligence (AI), and a highly eFicient logistics network to meet the growing
           demand for instant gratification. Our AI-driven platform optimizes inventory management,
           personalizes customer experiences, and ensures that whatever you need is just a few
           clicks away.
           Founded on the principles of speed, reliability, and customer satisfaction, NEXT is
           redefining the standards of online shopping. We are dedicated to creating a shopping
           experience that is not only fast but also sustainable and tailored to the needs of our
           customers.
           Join us as we lead the charge in the Q-commerce space, transforming the way the world
           shops, one instant delivery at a time.
        """.trimIndent(),
            modifier
                .fillMaxWidth(1f)
                .padding(top = 20.dp))

    }


}