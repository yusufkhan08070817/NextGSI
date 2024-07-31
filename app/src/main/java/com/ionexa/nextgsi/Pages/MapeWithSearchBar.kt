package com.ionexa.nextgsi.Pages

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.ionexa.nextgsi.Components.MapeComp
import com.ionexa.nextgsi.MVVM.MapeKCMVVM
import com.ionexa.nextgsi.SingleTon.Locatation
import com.ionexa.nextgsi.SingleTon.getSuggestions
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapeWithSerchBar(modifier: Modifier = Modifier,mapeKCMVVM: MapeKCMVVM) {
    var searchQuery by remember { mutableStateOf("") }
    var suggestions by remember { mutableStateOf(listOf<String>()) }
    var showsuggestion by remember {
        mutableStateOf(false)
    }
    val coroutineScope = rememberCoroutineScope()
   mapeKCMVVM.searchByCordinates(Locatation.latitude,Locatation.longitude)
    Column(
        Modifier
            .fillMaxWidth(1f)
            .fillMaxHeight(1f)
            .padding(top = 30.dp, start = 10.dp, end = 10.dp)
    ) {


        Card(
            modifier = Modifier
                .fillMaxWidth(1f)
                .padding(10.dp),
        ) {
            TextField(
                value = searchQuery,
                placeholder = {
                    Text(text = "Search Locatation")
                },
                onValueChange = { query ->
                    searchQuery = query
                    showsuggestion=true
                    coroutineScope.launch {
                        suggestions = getSuggestions(query)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.White,
                    cursorColor = Color.Black,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ), trailingIcon = {
                    IconButton(onClick = {  mapeKCMVVM.updateLocataionString(searchQuery);
                        mapeKCMVVM.searchLocation();
                        showsuggestion=false}) {
                        Icon(imageVector = Icons.Default.Search, contentDescription ="serchLocatation" )
                    }
                }
            )

        }
        AnimatedVisibility(visible = showsuggestion) {
            Card(colors = CardDefaults.cardColors(containerColor = Color.White), elevation = CardDefaults.elevatedCardElevation(12.dp)) {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(suggestions) { suggestion ->
                        Text(text = suggestion, modifier = Modifier
                            .padding(8.dp)
                            .clickable {
                                searchQuery = ""
                                searchQuery = suggestion
                                mapeKCMVVM.updateLocataionString(suggestion);
                                mapeKCMVVM.searchLocation();
                                showsuggestion = false
                                Locatation.choosenlocatation=suggestion
                                val (city, state, country) = splitAddress(suggestion)
                                Locatation.city=city
                                Locatation.state=state
                                Locatation.country=country
                            })
                    }


                }
            }
        }
        MapeComp(context = LocalContext.current, MapViewModel = mapeKCMVVM, )
    }
}
fun splitAddress(address: String): Triple<String, String, String> {
    // Split the address based on commas
    val parts = address.split(",").map { it.trim() }

    // Ensure there are at least 3 parts: city, state, and country
    return if (parts.size >= 3) {
        Triple(parts[0], parts[1], parts[2])
    } else {
        // Handle cases where the address format might not be as expected
        Triple("", "", "")
    }
}