package com.ionexa.nextgsi.FBFireBase

import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import com.ionexa.nextgsi.DataClass.SelectedFile
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.UUID

class FireBaseStorage {

    private val storage: FirebaseStorage by lazy {
        FirebaseStorage.getInstance()
    }

    // Upload a single image with progress
    suspend fun uploadSingleImage(
        imageUri: Uri,
        path: String,
        onProgress: (Double) -> Unit,
        onSuccess: (String) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        try {
            val url = uploadFile(
                fileUri = imageUri,
                filePath = "${path}/${UUID.randomUUID()}.jpg",
                onProgress = onProgress
            )
            if (url != null) {
                onSuccess(url)
            } else {
                onFailure(Exception("Failed to get URL"))
            }
        } catch (e: Exception) {
            onFailure(e)
        }
    }

    // Upload multiple images with progress
    suspend fun uploadMultipleImages(
        imageUris: List<SelectedFile>,
        path: String,
        onProgress: (Double) -> Unit,
        onSuccess: (List<String>) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        try {
            val imageUrls = mutableListOf<String>()
            var totalProgress = 0.0
            val totalFiles = imageUris.size

            for ((index, imageUri) in imageUris.withIndex()) {
                val filePath = "${path}/${UUID.randomUUID()}.jpg"
                uploadFile(
                    fileUri = imageUri.uri,
                    filePath = filePath,
                    onProgress = { progress ->
                        totalProgress = (index + progress / totalFiles) / totalFiles
                        onProgress(totalProgress)
                    }
                )?.let { url ->
                    imageUrls.add(url)
                }
            }
            onSuccess(imageUrls)
        } catch (e: Exception) {
            onFailure(e)
        }
    }

    // Upload a PDF file with progress
    suspend fun uploadPdf(
        pdfUri: Uri,
        path: String,
        onProgress: (Double) -> Unit,
        onSuccess: (String) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        try {
            val url = uploadFile(
                fileUri = pdfUri,
                filePath = "${path}/${UUID.randomUUID()}.pdf",
                onProgress = onProgress
            )
            if (url != null) {
                onSuccess(url)
            } else {
                onFailure(Exception("Failed to get URL"))
            }
        } catch (e: Exception) {
            onFailure(e)
        }
    }

    // General method to upload any file type with progress
    private suspend fun uploadFile(
        fileUri: Uri,
        filePath: String,
        onProgress: (Double) -> Unit
    ): String? {
        val storageRef = storage.reference.child(filePath)
        val uploadTask = storageRef.putFile(fileUri)

        return try {
            uploadTask.addOnProgressListener { snapshot ->
                val progress = (100.0 * snapshot.bytesTransferred / snapshot.totalByteCount)
                onProgress(progress)
            }.await()

            storageRef.downloadUrl.await().toString()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    // Delete a file from Firebase Storage
    suspend fun deleteFile(filePath: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        val fileRef = storage.reference.child(filePath)
        try {
            fileRef.delete().await()
            onSuccess()
        } catch (e: Exception) {
            onFailure(e)
        }
    }

    // Update a file: delete old and upload new
    suspend fun updateFile(
        oldFilePath: String,
        newFileUri: Uri,
        newFilePath: String,
        onProgress: (Double) -> Unit,
        onSuccess: (String) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        var corutine= CoroutineScope(Dispatchers.Main)
        try {
            // Delete old file
            deleteFile(
                filePath = oldFilePath,
                onSuccess = {
                    // Upload new file
                    corutine.launch {
                        uploadFile(
                            fileUri = newFileUri,
                            filePath = newFilePath,
                            onProgress = onProgress
                        )?.let { url ->
                            onSuccess(url)
                        } ?: onFailure(Exception("Failed to get URL"))
                    }

                },
                onFailure = onFailure
            )
        } catch (e: Exception) {
            onFailure(e)
        }
    }
}
