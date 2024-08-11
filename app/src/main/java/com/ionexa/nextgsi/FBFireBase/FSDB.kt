package com.ionexa.nextgsi.FBFireBase

import android.util.Log

import com.ionexa.nextgsi.SingleTon.common
import com.ionexa.nextgsi.SingleTon.common.db
import kotlinx.coroutines.tasks.await
import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor

class FSDB {

    fun <T : Any> T.toHashMap(): HashMap<String, Any?> {
        val map = HashMap<String, Any?>()
        this::class.members
            .filter { it is kotlin.reflect.KProperty1<*, *> }
            .forEach { property ->
                map[property.name] = (property as kotlin.reflect.KProperty1<Any, *>).get(this)
            }
        return map
    }

    suspend fun uploadDataToFireStoreDB(
        data: HashMap<String, Any?>,
        collectionName: String,
        documentName: String,
        onsuccess: (log: String) -> Unit,
        onfailure: (log: String) -> Unit
    ) {
        try {
            db.collection(collectionName).document(documentName).set(data).await()
            Log.d(common.FBDBTAG, "DocumentSnapshot added with ID: ")
            onsuccess("success")
        } catch (e: Exception) {
            Log.w(common.FBDBTAG, "Error adding document", e)
            onfailure(e.toString())
        }
    }

    suspend fun getDataFromFireStoreDB(
        collectionName: String,
        documentName: String,
        onSuccess: (data: Map<String, Any>?) -> Unit,
        onFailure: (log: String) -> Unit
    ) {
        try {
            val document = db.collection(collectionName).document(documentName).get().await()
            if (document.exists()) {
                onSuccess(document.data)
            } else {
                onSuccess(null)
                Log.d(common.FBDBTAG, "No such document")
            }
        } catch (e: Exception) {
            Log.w(common.FBDBTAG, "Error getting document", e)
            onFailure(e.toString())
        }
    }

    suspend fun updateDataInFireStoreDB(
        collectionName: String,
        documentName: String,
        updates: Map<String, Any?>,
        onSuccess: (log: String) -> Unit,
        onFailure: (log: String) -> Unit
    ) {
        try {
           if (updates!=null){
               db.collection(collectionName).document(documentName).update(updates).await()
           }else
           {
               Log.e("null data","null data we get ")
           }

            Log.d(common.FBDBTAG, "Document successfully updated")
            onSuccess("Document successfully updated")
        } catch (e: Exception) {
            Log.w(common.FBDBTAG, "Error updating document", e)
            onFailure(e.toString())
        }
    }

    suspend fun deleteDataFromFireStoreDB(
        collectionName: String,
        documentName: String,
        onSuccess: (log: String) -> Unit,
        onFailure: (log: String) -> Unit
    ) {
        try {
            db.collection(collectionName).document(documentName).delete().await()
            Log.d(common.FBDBTAG, "Document successfully deleted")
            onSuccess("Document successfully deleted")
        } catch (e: Exception) {
            Log.w(common.FBDBTAG, "Error deleting document", e)
            onFailure(e.toString())
        }
    }

    fun <T : Any> hashMapToDataClass(map: Map<String, Any>, dataClass: KClass<T>): T? {
        val constructor = dataClass.primaryConstructor ?: return null
        val args = constructor.parameters.associateWith { param ->
            map[param.name]
        }
        return constructor.callBy(args)
    }
}
