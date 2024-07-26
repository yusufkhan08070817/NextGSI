package com.ionexa.nextgsi.Pages

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
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
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun NaviGatationWithFloatingActionButton(
    naveHight: Dp = 120.dp,
    NormalButtonSize: Dp = 60.dp,
    FloatingActionButtonIconSize: Dp = 70.dp,
    NormalIconList:List<ImageVector> = listOf(
        Icons.Default.Home,
        Icons.Default.Search,
        Icons.Default.Menu,
        Icons.Default.AccountCircle),
    NormalIconColor:Color=Color.Black,
    FLoatingActionButtonIcon: ImageVector =Icons.Default.Home,
    FoatingsButtonElevatation: Dp=20.dp,
    NaveContainerColor: Color = Color.White,
    FloatingButtonColor: Color = Color.White,
    FolatingButtonIconColor:Color=Color.DarkGray
    ,

    ButtonOne: () -> Unit = {},
    ButtonTwo: () -> Unit = {},
    ButtonThree: () -> Unit = {},
    ButtonFour: () -> Unit = {},
    FloatingButton: () -> Unit = {},
) {
var position by remember {
    mutableStateOf(1)
}
    val boxheight = (naveHight / 2) + 10.dp
    val Canvasheight = (naveHight / 2) + 20.dp
    Box(
        modifier = Modifier
            .fillMaxWidth(1f)
            .height(naveHight)
    ) {
        Column(
            modifier = Modifier.height(naveHight + 10.dp),
            verticalArrangement = Arrangement.Bottom
        ) {
            Box(
                Modifier
                    .fillMaxWidth(1f)
                    .height(boxheight)
            )
            {

                Canvas(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(Canvasheight)
                ) {

                    val oneThirdOfWidth = size.width / 3
                    val arcHeight = naveHight.toPx()

                    // Create the path for the outer rectangle
                    val path = Path().apply {
                        // Draw the outer rectangle
                        moveTo(0f, 0f)
                        lineTo(size.width, 0f)
                        lineTo(size.width, size.height)
                        lineTo(0f, size.height)

                        close()

                        // Create a hole for the arch
                        val arcRect = Rect(
                            left = (size.width / 2f) - (size.width / 8),
                            top = -arcHeight / 3.2f,
                            right = size.width / 2f + (size.width / 8),
                            bottom = arcHeight / 3.2f
                        )


                        // Move to the starting point of the arc
                        moveTo(oneThirdOfWidth, 0f)
                        arcTo(
                            rect = arcRect,
                            startAngleDegrees = 0f,
                            sweepAngleDegrees = 180f,
                            forceMoveTo = false
                        )
                        lineTo(2 * oneThirdOfWidth, 0f)

                    }

                    // Set the fill type to even-odd to create the hole
                    path.fillType = PathFillType.EvenOdd

                    // Draw the path on the canvas
                    drawPath(
                        path = path,
                        color = NaveContainerColor
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(1f),
                    horizontalArrangement = Arrangement.Absolute.Right
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(0.36f)
                            .height(naveHight - 20.dp),
                    ) {

                        IconButton(onClick = { ButtonThree();position=3}, modifier = Modifier.padding(10.dp)) {
                            Icon(
                                imageVector = NormalIconList[2],
                                contentDescription = "Search",
                                tint = if (position==3) Color.White else NormalIconColor,
                                modifier = Modifier
                                    .width(NormalButtonSize)
                                    .height(NormalButtonSize)
                            )

                        }
                        IconButton(onClick = { ButtonFour() ;position=4}, modifier = Modifier.padding(10.dp)) {
                            Icon(
                                imageVector = NormalIconList[3],
                                contentDescription = "Search",
                                tint = if (position==4) Color.White else NormalIconColor,
                                modifier = Modifier
                                    .width(NormalButtonSize)
                                    .height(NormalButtonSize)
                            )

                        }
                    }
                }

                Row(modifier = Modifier.fillMaxWidth(1f)) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(0.36f)
                            .height(naveHight - 20.dp),
                    ) {

                        IconButton(onClick = { ButtonOne();position=1 }, modifier = Modifier.padding(10.dp)) {
                            Icon(
                                imageVector = NormalIconList[0],
                                contentDescription = "Search",
                                tint = if (position==1) Color.White else NormalIconColor,
                                modifier = Modifier
                                    .width(NormalButtonSize)
                                    .height(NormalButtonSize)
                            )

                        }
                        IconButton(onClick = { ButtonTwo();position=2 }, modifier = Modifier.padding(10.dp)) {

                            Icon(
                                imageVector = NormalIconList[1],
                                contentDescription = "Search",
                                tint = if (position==2) Color.White else NormalIconColor,
                                modifier = Modifier
                                    .width(NormalButtonSize)
                                    .height(NormalButtonSize)
                            )

                        }
                    }
                }


            }
        }
        Column(
            modifier = Modifier
                .height(naveHight + 30.dp)
                .fillMaxWidth(1f),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                modifier = Modifier
                    .width(Canvasheight)
                    .height(Canvasheight),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(Canvasheight),
                colors = CardDefaults.cardColors(containerColor = FloatingButtonColor),
                elevation = CardDefaults.elevatedCardElevation(FoatingsButtonElevatation)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(1f),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    IconButton(onClick = { FloatingButton() }) {
                        Icon(
                            imageVector = FLoatingActionButtonIcon,
                            contentDescription = "Menu",
                            tint = FolatingButtonIconColor,
                            modifier = Modifier
                                .width(FloatingActionButtonIconSize)
                                .height(FloatingActionButtonIconSize)
                        )

                    }
                }
            }
        }
    }
}