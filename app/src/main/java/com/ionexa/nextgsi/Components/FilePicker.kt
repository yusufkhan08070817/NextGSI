package com.ionexa.nextgsi.Components

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.ionexa.nextgsi.DataClass.SelectedFile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun FilePicker(onFilesSelected: (List<SelectedFile>) -> Unit) {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenMultipleDocuments()
    ) { uris: List<Uri> ->
        val selectedFiles = uris.mapNotNull { uri ->
            getFileDetails(context, uri)
        }
        onFilesSelected(selectedFiles)
    }

    LaunchedEffect(Unit) {
        launcher.launch(arrayOf("*/*"))
    }
}

fun getFileDetails(context: Context, uri: Uri): SelectedFile? {
    val contentResolver = context.contentResolver
    val mimeType = contentResolver.getType(uri)
    var name: String? = null

    contentResolver.query(uri, null, null, null, null)?.use { cursor ->
        val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        if (cursor.moveToFirst()) {
            name = cursor.getString(nameIndex)
        }
    }

    return SelectedFile(uri, mimeType, name)
}