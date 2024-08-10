package com.ionexa.nextgsi.FBFireBase


import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class FireBaseStoreDBMyClass {

    val firestore: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }

    // Create a new document in the specified collection
    suspend inline fun <reified T> createDocument(
        path: String,
        document: T,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        try {
            val documentMap = Json.encodeToString(document)
            val documentData = Json.decodeFromString<Map<String, Any>>(documentMap)
            firestore.collection(path)
                .add(documentData)
                .await()
            onSuccess()
        } catch (e: Exception) {
            onFailure(e)
        }
    }

    // Read a document by ID from the specified collection
    suspend inline fun <reified T> readDocumentById(
        path: String,
        id: String,
        clazz: Class<T>,
        onSuccess: (T?) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        try {
            val documentSnapshot = firestore.collection(path)
                .document(id)
                .get()
                .await()
            val documentMap = documentSnapshot.data
            val documentJson = Json.encodeToString(documentMap)
            val document = Json.decodeFromString<T>(documentJson)
            onSuccess(document)
        } catch (e: Exception) {
            onFailure(e)
        }
    }

    // Read all documents from the specified collection
    suspend fun <T> readAllDocuments(
        path: String,
        clazz: Class<T>,
        onSuccess: (List<Any>) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        try {
            val querySnapshot = firestore.collection(path)
                .get()
                .await()
            val documents = querySnapshot.documents.mapNotNull { documentSnapshot ->
                val documentMap = documentSnapshot.data
                val documentJson = Json.encodeToString(documentMap)
                Json.decodeFromString(documentJson)
            }
            onSuccess(documents)
        } catch (e: Exception) {
            onFailure(e)
        }
    }

    // Update a document in the specified collection
    suspend inline fun <reified T> updateDocument(
        path: String,
        id: String,
        document: T,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        try {
            val documentMap = Json.encodeToString(document)
            val documentData = Json.decodeFromString<Map<String, Any>>(documentMap)
            firestore.collection(path)
                .document(id)
                .set(documentData)
                .await()
            onSuccess()
        } catch (e: Exception) {
            onFailure(e)
        }
    }

    // Delete a document by ID from the specified collection
    suspend fun deleteDocument(
        path: String,
        id: String,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        try {
            firestore.collection(path)
                .document(id)
                .delete()
                .await()
            onSuccess()
        } catch (e: Exception) {
            onFailure(e)
        }
    }
}