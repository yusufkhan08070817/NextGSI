package com.ionexa.nextgsi.Pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.*
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter

data class Product(val id: Int, val name: String, val price: String, val imageUrl: String)

@Composable
fun ProductList(products: List<Product>, onDelete: (Product) -> Unit, onEdit: (Product) -> Unit) {
    LazyColumn {
        items(products) { product ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(text = product.name, style = MaterialTheme.typography.bodySmall)
                    Text(text = "Price: ${product.price}")
                    Image(
                        painter = rememberImagePainter(product.imageUrl),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .padding(top = 8.dp)
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Button(onClick = { onDelete(product) }) {
                            Text(text = "Delete")
                        }
                        Button(onClick = { onEdit(product) }) {
                            Text(text = "Edit")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun EditProductDialog(product: Product, onConfirm: (Product) -> Unit, onCancel: () -> Unit) {
    var editedName by remember { mutableStateOf(product.name) }
    var editedPrice by remember { mutableStateOf(product.price) }

    AlertDialog(
        onDismissRequest = onCancel,
        title = { Text(text = "Edit com.ionexa.nextgsi.Pages.Product") },
        text = {
            Column {
                BasicTextField(
                    value = editedName,
                    onValueChange = { editedName = it },
                    textStyle = TextStyle(color = Color.Black)
                )
                Spacer(modifier = Modifier.height(8.dp))
                BasicTextField(
                    value = editedPrice,
                    onValueChange = { editedPrice = it },
                    textStyle = TextStyle(color = Color.Black)
                )
            }
        },
        confirmButton = {
            Button(onClick = { onConfirm(product.copy(name = editedName, price = editedPrice)) }) {
                Text(text = "Save")
            }
        },
        dismissButton = {
            Button(onClick = onCancel) {
                Text(text = "Cancel")
            }
        }
    )
}
