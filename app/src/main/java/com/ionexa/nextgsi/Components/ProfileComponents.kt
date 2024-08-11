package com.ionexa.nextgsi.Components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ionexa.nextgsi.R
import com.ionexa.nextgsi.ui.theme.IndigoHeading
import com.ionexa.nextgsi.ui.theme.Plum
import com.ionexa.nextgsi.ui.theme.SlateBlue

@Composable
fun RectangleWithCurve(modifier: Modifier = Modifier,profileimageurl:String,iseidtable:Boolean,onclick:()->Unit,editable:()->Unit) {
    Box(modifier = Modifier
        .fillMaxWidth(1f)
        .height(300.dp))
    {

        Canvas(modifier = Modifier
            .fillMaxWidth(1f)
            .height(200.dp)) {
            val brush = Brush.linearGradient(listOf(IndigoHeading, SlateBlue))

            val path: Path = Path().apply {
                drawCircle(brush, radius = size.width/1.4f, center = Offset(size.width/2,size.height/38f))
                close()
            }
            drawPath(path, brush = brush)
        }

        Column(modifier = Modifier
            .fillMaxWidth(1f)
            .height(330.dp)
            .clickable { onclick() }
            , horizontalAlignment = Alignment.CenterHorizontally,verticalArrangement = Arrangement.Bottom) {
            Card(modifier= Modifier
                .fillMaxWidth(0.4f)
                .fillMaxHeight(0.48f), onClick = { onclick()},
                colors = CardDefaults.cardColors(containerColor = if(iseidtable) Color.White else Plum), elevation = CardDefaults.elevatedCardElevation(2.dp), shape = CircleShape
            ) {
               Card ( modifier = Modifier
                   .fillMaxSize(1f).padding(5.dp), shape = CircleShape, colors = CardDefaults.cardColors(containerColor =  if(iseidtable) Color.White else Plum)){
                   AsyncImage(
                       model = profileimageurl,
                       contentDescription =null,
                       contentScale = ContentScale.Fit,
                       modifier = Modifier
                           .clip(shape = CircleShape)
                           .fillMaxSize(1f)

                   )
               }
            }
        }
        Row(modifier = Modifier
            .fillMaxWidth(0.8f)
            .height(330.dp)
            , horizontalArrangement = Arrangement.Absolute.Right, verticalAlignment = Alignment.Bottom) {
            Card(onClick = {editable()},shape = CircleShape, colors = CardDefaults.cardColors(containerColor = Color.White), elevation = CardDefaults.elevatedCardElevation(10.dp)) {
                IconButton(onClick = { editable() }) {
                    Icon(imageVector = Icons.Default.Edit, contentDescription ="Edit" )

                }
            }
        }
    }

}
