package com.ionexa.nextgsi.Pages

import android.widget.Toast
import android.widget.ToggleButton
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.ionexa.nextgsi.FBFireBase.FSDB
import com.ionexa.nextgsi.SingleTon.NaveLabels
import com.ionexa.nextgsi.SingleTon.Navigation
import com.ionexa.nextgsi.SingleTon.common
import kotlinx.coroutines.launch

@Composable
fun SellerInfo() {
    var fsdb = FSDB()
    var context = LocalContext.current
    var coroutineScope = rememberCoroutineScope()
    var sellerDescription by remember { mutableStateOf("") }
    var sellerChatOption by remember { mutableStateOf(false) }
    var returnPolicy by remember { mutableStateOf("") }
    var responseTime by remember { mutableStateOf("") }
    var socialMediaLinks by remember { mutableStateOf("") }
    var data by remember { mutableStateOf(mutableMapOf<String, Any?>()) }
    var sopeid = generateUniqueId(null)
    LaunchedEffect(key1 = true) {
        fsdb.getDataFromFireStoreDB("users", common.myid.value, onSuccess = {
            if (it != null) {
                data = it["personel_info"] as MutableMap<String, Any?>

            }
        }) {
Toast.makeText(context, "Failed $it", Toast.LENGTH_SHORT).show()
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize(1f)
            .padding(8.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxHeight(0.15f)
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Seller Information")
            }
        }
        Spacer(modifier = Modifier.fillMaxHeight(0.12f))
        Textcustomfild(
            onset = { sellerDescription = it },
            label = "Seller Description",
            value = sellerDescription
        )
        Spacer(modifier = Modifier.fillMaxHeight(0.12f))
        ChatOptionToggle(label = "Chat Option",
            value = sellerChatOption,
            onValueChange = { sellerChatOption = it })
        Textcustomfild(onset = { returnPolicy = it }, label = "Return Policy", value = returnPolicy)
        Textcustomfild(onset = { responseTime = it }, label = "Response Time", value = responseTime)
        Textcustomfild(
            onset = { socialMediaLinks = it },
            label = "Social Media Links",
            value = socialMediaLinks
        )
        Spacer(modifier = Modifier.fillMaxHeight(0.12f))
        Button(onClick = {
            coroutineScope.launch {
                data["sellerDescription"] = sellerDescription
                data["sellerChatOption"] = sellerChatOption
                data["returnPolicy"] = returnPolicy
                data["responseTime"] = responseTime
                data["socialMediaLinks"] = socialMediaLinks
                data["shopid"] = sopeid.toString()

                fsdb.uploadDataToFireStoreDB(data = hashMapOf("personel_info" to data) ,
                    collectionName = "users",
                    documentName = common.myid.value,
                    onsuccess = {
                        Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show(

                        )
                        Navigation.navController.navigate(NaveLabels.seller)
                    },
                    onfailure = {
                        Toast.makeText(context, "Failed $it", Toast.LENGTH_SHORT).show()
                    }
                )

                // fsdb.uploadDataToFireStoreDB()
            }

        }) {
            Text(text = "Save")
        }

    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Textcustomfild(onset: (String) -> Unit, label: String, value: String) {
    Spacer(modifier = Modifier.fillMaxHeight(0.05f))
    Card(
        modifier = Modifier
            .fillMaxWidth(1f)
            .fillMaxHeight(0.14f)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = value,
                modifier = Modifier.fillMaxWidth(1f),
                onValueChange = { onset(it) },
                label = { Text(text = label) },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.White,
                    cursorColor = Color.Black,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent


                )
            )
        }
    }
}

@Composable
fun ChatOptionToggle(
    label: String, value: Boolean, onValueChange: (Boolean) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(1f)
        )
        Switch(
            checked = value, onCheckedChange = onValueChange
        )
    }
}
