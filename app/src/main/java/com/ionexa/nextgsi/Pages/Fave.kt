package com.ionexa.nextgsi.Pages

import android.media.Image
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.ionexa.nextgsi.DataClass.FaveList
import com.ionexa.nextgsi.FBFireBase.FSDB
import com.ionexa.nextgsi.SingleTon.common
import com.ionexa.nextgsi.ui.theme.Mediumpurple

@Composable
fun FaveIteam(modifier: Modifier = Modifier) {
    val fbfs = FSDB()
    var listdata = remember { mutableStateListOf<Map<String, Any>>() }

    LaunchedEffect(true) {
        fbfs.getDataFromFireStoreDB("users", common.myid.value, onSuccess = { data ->
            val abd = data?.get("fave") as? List<Map<String, Any>> ?: emptyList()
            listdata.clear()
            listdata.addAll(abd)
            Log.e("abd", listdata.getOrNull(0)?.get("name").toString())
        }){}
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp), colors = CardDefaults.cardColors(Mediumpurple)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 20.dp, start = 20.dp),

                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(text = "Favorite List", color = Color.White,fontSize = 20.sp)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            items(listdata) { item ->
                Log.e("abd.image", item["image"].toString())
                iteams(item["name"].toString(), item["image"].toString())
            }
        }
    }
}

@Composable
fun iteams(name: String, image: String) {
    var fave by remember {
        mutableStateOf(false)
    }
    Card(
        onClick = { /*TODO*/ }, modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize().padding(10.dp)
                ,
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(
                modifier = Modifier
                    .fillMaxHeight(1f)
                    .fillMaxWidth(0.1f)
            )
            AsyncImage(
                model = common.decodeFromBase64(image),
                contentDescription = "product image",
                modifier = Modifier
                    .size(50.dp)
                    .clip(RoundedCornerShape(50.dp)) // Half of the image size to make it round
            )

            Spacer(
                modifier = Modifier
                    .fillMaxHeight(1f)
                    .fillMaxWidth(0.1f)
            )
            Text(text = name)
            Spacer(
                modifier = Modifier
                    .fillMaxHeight(1f)
                    .fillMaxWidth(0.1f)
            )
            IconButton(onClick = { fave = !fave }) {
                AnimatedVisibility(visible = fave) {
                    Icon(imageVector = Icons.Default.Favorite, contentDescription = "")
                }
                AnimatedVisibility(visible = !fave) {
                    Icon(imageVector = Icons.Default.Favorite, contentDescription = "", tint = androidx.compose.ui.graphics.Color.Red)
                }

            }

        }
    }
}