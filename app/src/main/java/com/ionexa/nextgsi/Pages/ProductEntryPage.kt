package com.ionexa.nextgsi.Pages

import android.net.Uri

import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.ionexa.nextgsi.Components.ProgressDialog
import com.ionexa.nextgsi.Components.getFileDetails
import com.ionexa.nextgsi.DataClass.ExtraInfo
import com.ionexa.nextgsi.DataClass.ProductTypeId
import com.ionexa.nextgsi.DataClass.SelectedFile
import com.ionexa.nextgsi.FBFireBase.FSDB
import com.ionexa.nextgsi.FBFireBase.FireBaseStorage
import com.ionexa.nextgsi.SingleTon.common
import kotlinx.coroutines.launch
import kotlin.random.Random

@Composable
fun ProductEntryPage() {
    var fbs = FireBaseStorage()
var coroutineScope= rememberCoroutineScope()
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var stock by remember { mutableStateOf("") }
    var variants by remember { mutableStateOf("") }
    var discount by remember { mutableStateOf("") }
    var shippingInfo by remember { mutableStateOf("") }


    var images by remember { mutableStateOf(listOf<Uri>()) }
    var submit by remember {
        mutableStateOf(false)
    }
    var SelectedFile by remember {
        mutableStateOf(listOf<SelectedFile>())
    }
    val context = LocalContext.current
    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetMultipleContents()) { uris ->

            images = uris.take(10)

            SelectedFile = uris.mapNotNull { uri ->
                getFileDetails(context, uri)
            }

        }
    var dalaclass by remember {
        mutableStateOf(ProductTypeId())
    }
    var loaderror by remember {
        mutableStateOf(false)
    }
    var prograssbar by remember {
        mutableStateOf(0.0)
    }
    var fsdb = FSDB()
    LaunchedEffect(key1 = submit) {
        if (submit) {
            loaderror = true
            if (SelectedFile.isNotEmpty()) {
                fbs.uploadMultipleImages(
                    SelectedFile,
                    path = "${common.myid.value}/product/${category}",
                    onProgress = { prograssbar = it },
                    onSuccess = { uploadedImages ->
                        var images = uploadedImages.map { common.encodeToBase64(it.toString()) }
                        dalaclass.images = images

                        // Launch a coroutine to handle suspend functions
                        coroutineScope.launch {
                            try {
                                // Update Firestore after image upload
                                dalaclass.productid=generateUniqueId(null).toString()
                                dalaclass.name = name
                                dalaclass.extraInfo = ExtraInfo(description)
                                dalaclass.price = price.toString()
                                dalaclass.productQuantity = stock.toString()
                                dalaclass.offer = discount.toString()
                                dalaclass.productDetails = shippingInfo
                                dalaclass.address=common.locatation
                                dalaclass.rating="0"
                                dalaclass.review=""
                                dalaclass.seller=common.myid.value
                                dalaclass.types=category
                                dalaclass.availableStock=true.toString()


                                fsdb.updateDataInFirestore(
                                    collectionName = "Product",
                                    documentName = category,
                                    fieldName = "product",
                                    data = dalaclass,
                                    onsuccess = {
                                        Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
                                    },
                                    onfailure = {
                                        Log.e("uploading", it)
                                        Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                                    }
                                )
                                var list= listOf(dalaclass)
                                fsdb.updateDataInFirestore("users", common.myid.value, "products", list, onsuccess = {
                                    Log.e("error","sucess")
                                    Toast.makeText(context, "catagory updated", Toast.LENGTH_SHORT).show()
                                }, onfailure = {
                                    Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                                    Log.e("error",it)
                                })

                                fsdb.getDataFromFireStoreDB("productCatagory", category, onSuccess = {
                                    var data = it as MutableList<String>
                                    data.add(category)
                                }) {
                                    val list = listOf(category)
                                    fsdb.uploadListToFirestore(
                                        "productCatagory",
                                        category,
                                        list,
                                        onSuccess = {
                                            loaderror = false
                                            submit=false
                                            images = emptyList()
                                        }
                                    ) {}
                                }

                            } catch (e: Exception) {
                                Log.e("Firestore", "Error updating Firestore", e)
                                Toast.makeText(context, "Firestore update failed", Toast.LENGTH_SHORT).show()
                            }
                        }
                    },
                    onFailure = {
                        Toast.makeText(context, "Image upload failed", Toast.LENGTH_SHORT).show()
                    }
                )
            } else {
                Toast.makeText(context, "Select Images", Toast.LENGTH_SHORT).show()
            }
        }
    }



    AnimatedVisibility(visible = !loaderror) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
                .background(Color(0xFFF5F5F5))
                .padding(bottom = 32.dp)
        ) {
            Text(
                text = "Add New Product",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            TextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )
            TextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )
            TextField(
                value = category,
                onValueChange = { category = it },
                label = { Text("Category") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )
            TextField(
                value = price,
                onValueChange = { if (it.toFloatOrNull() != null) price = it },
                label = { Text("Price") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            TextField(
                value = stock,
                onValueChange = { if (it.toIntOrNull() != null) stock = it },
                label = { Text("Stock") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            TextField(
                value = variants,
                onValueChange = { variants = it },
                label = { Text("Variants") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )
            TextField(
                value = discount,
                onValueChange = { if (it.toFloatOrNull() != null) discount = it },
                label = { Text("Discount") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            TextField(
                value = shippingInfo,
                onValueChange = { shippingInfo = it },
                label = { Text("Shipping Info") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp)
            )
            Button(
                onClick = {
                    launcher.launch("image/*")
                    dalaclass.images = images.map { it.toString() }
                }, modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 5.dp)
            ) {
                Text("Add Images")
            }

            LazyRow(
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                items(images) { imageUri ->

                    Box(modifier = Modifier.size(100.dp)) {
                        Image(
                            painter = rememberImagePainter(imageUri),
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize()
                        )
                        IconButton(
                            onClick = { images = images.filter { it != imageUri } },
                            modifier = Modifier.align(Alignment.TopEnd)
                        ) {
                            Icon(
                                Icons.Default.Close,
                                contentDescription = "Remove Image",
                                tint = Color.Red
                            )
                        }
                    }
                }
            }

            Button(
                onClick = { submit=true }, modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp)
            ) {
                Text("Save Product $submit")
            }
        }
    }
    AnimatedVisibility(visible = loaderror) {
        ProgressDialog(loaderror, prograssbar) {
            loaderror = false
        }
    }
}

fun generateUniqueId(existingIds: Set<Long>? = null): Long {
    var uniqueId: Long

    do {
        uniqueId = Random.nextLong(1000000000L, 10000000000L)
    } while (existingIds?.contains(uniqueId) == true)

    return uniqueId
}