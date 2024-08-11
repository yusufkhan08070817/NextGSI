package com.ionexa.nextgsi.Components

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.ionexa.nextgsi.DataClass.SelectedFile

@Composable
fun FilePicker(onFilesSelected: (List<SelectedFile>) -> Unit) {
    val context = LocalContext.current
    // Create an ActivityResultLauncher for opening files
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenMultipleDocuments()
    ) { uris: List<Uri> ->
        val selectedFiles = uris.mapNotNull { uri ->
            getFileDetails(context, uri)
        }
        onFilesSelected(selectedFiles)
    }

    // Launch the file picker when the composable is first displayed
    LaunchedEffect(Unit) {
        launcher.launch(arrayOf("*/*"))
    }
}

fun getFileDetails(context: Context, uri: Uri): SelectedFile? {
    val contentResolver = context.contentResolver
    val mimeType = contentResolver.getType(uri)
    var name: String? = null

    contentResolver.query(uri, null, null, null, null)?.use { cursor ->
        if (cursor.moveToFirst()) {
            val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            if (nameIndex != -1) {
                name = cursor.getString(nameIndex)
            }
        }
    }

    // Default to using the last segment of the URI if the name couldn't be retrieved
    name = name ?: uri.lastPathSegment

    return if (name != null) {
        SelectedFile(uri, mimeType, name)
    } else {
        null
    }
}
