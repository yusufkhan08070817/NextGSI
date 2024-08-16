package com.ionexa.nextgsi.Pages

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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

@Composable
fun ProductEntryPage() {
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var stock by remember { mutableStateOf("") }
    var variants by remember { mutableStateOf("") }
    var discount by remember { mutableStateOf("") }
    var shippingInfo by remember { mutableStateOf("") }
    var images by remember { mutableStateOf(listOf<Uri>()) }

    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetMultipleContents()) { uris ->
        images = uris.take(10)
    }

    fun validateAndSave() {
        when {
            name.isEmpty() -> Toast.makeText(context, "Name is required", Toast.LENGTH_SHORT).show()
            description.isEmpty() -> Toast.makeText(context, "Description is required", Toast.LENGTH_SHORT).show()
            category.isEmpty() -> Toast.makeText(context, "Category is required", Toast.LENGTH_SHORT).show()
            price.isEmpty() -> Toast.makeText(context, "Price is required", Toast.LENGTH_SHORT).show()
            stock.isEmpty() -> Toast.makeText(context, "Stock is required", Toast.LENGTH_SHORT).show()
            variants.isEmpty() -> Toast.makeText(context, "Variants are required", Toast.LENGTH_SHORT).show()
            discount.isEmpty() -> Toast.makeText(context, "Discount is required", Toast.LENGTH_SHORT).show()
            shippingInfo.isEmpty() -> Toast.makeText(context, "Shipping Info is required", Toast.LENGTH_SHORT).show()
            images.size < 2 -> Toast.makeText(context, "At least 2 images are required", Toast.LENGTH_SHORT).show()
            else -> Toast.makeText(context, "Product saved successfully", Toast.LENGTH_SHORT).show()
        }
    }

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
            onClick = { launcher.launch("image/*") },
            modifier = Modifier
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
                        Icon(Icons.Default.Close, contentDescription = "Remove Image", tint = Color.Red)
                    }
                }
            }
        }

        Button(
            onClick = { validateAndSave() },
            modifier = Modifier.fillMaxWidth()
                .padding(bottom = 20.dp)
        ) {
            Text("Save Product")
        }
    }
}
