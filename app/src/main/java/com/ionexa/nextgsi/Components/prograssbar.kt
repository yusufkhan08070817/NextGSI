package com.ionexa.nextgsi.Components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun ProgressDialog(showDialog: Boolean, progress: Double, onDismissRequest: () -> Unit) {
    if (showDialog) {
        Dialog(onDismissRequest = { onDismissRequest() }) {
            Surface(
                shape = MaterialTheme.shapes.medium,
                color = MaterialTheme.colorScheme.background
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    LinearProgressIndicator(progress = progress.toFloat())
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = "Loading: ${(progress * 100).toInt()}%")
                }
            }
        }
    }
}

@Composable
fun SampleScreen() {
    var showDialog by remember { mutableStateOf(false) }
    var progress by remember { mutableStateOf(0.0) }

    LaunchedEffect(showDialog) {
        if (showDialog) {
            for (i in 0..100) {
                progress = i / 100.0
                kotlinx.coroutines.delay(50)  // Simulate progress update
            }
            showDialog = false  // Close dialog when progress is complete
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = { showDialog = true }) {
            Text("Show Progress Dialog")
        }

        ProgressDialog(showDialog = showDialog, progress = progress, onDismissRequest = { showDialog = false })
    }
}
