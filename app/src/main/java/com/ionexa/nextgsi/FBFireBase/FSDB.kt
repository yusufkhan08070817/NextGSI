package com.ionexa.nextgsi.FBFireBase

import android.util.Log
import com.ionexa.nextgsi.SingleTon.common
import com.ionexa.nextgsi.SingleTon.common.db

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

    fun uploadDataToFireStoreDB( data:HashMap<String,Any>,collectionName:String,documentName:String,onsuccess:(log:String)->Unit,onfailure:(log:String)->Unit){
        db.collection(collectionName).document(documentName)
            .set(data)
            .addOnSuccessListener { documentReference ->
                Log.d(common.FBDBTAG, "DocumentSnapshot added with ID: ${documentReference}")
                onsuccess(documentReference.toString())
            }
            .addOnFailureListener { e ->
                Log.w(common.FBDBTAG, "Error adding document", e)
                onfailure(e.toString())
            }
    }

    fun getDataFromFireStoreDB(
        collectionName: String,
        documentName: String,
        onSuccess: (data: Map<String, Any>?) -> Unit,
        onFailure: (log: String) -> Unit
    ) {
        db.collection(collectionName).document(documentName)
            .get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    onSuccess(document.data)
                } else {
                    onSuccess(null)
                }
            }
            .addOnFailureListener { e ->
                Log.w(common.FBDBTAG, "Error getting document", e)
                onFailure(e.toString())
            }
    }
    fun updateDataInFireStoreDB(
        collectionName: String,
        documentName: String,
        updates: Map<String, Any>,
        onSuccess: (log: String) -> Unit,
        onFailure: (log: String) -> Unit
    ) {
        db.collection(collectionName).document(documentName)
            .update(updates)
            .addOnSuccessListener {
                Log.d(common.FBDBTAG, "Document successfully updated")
                onSuccess("Document successfully updated")
            }
            .addOnFailureListener { e ->
                Log.w(common.FBDBTAG, "Error updating document", e)
                onFailure(e.toString())
            }
    }
    fun deleteDataFromFireStoreDB(
        collectionName: String,
        documentName: String,
        onSuccess: (log: String) -> Unit,
        onFailure: (log: String) -> Unit
    ) {       db.collection(collectionName).document(documentName)
            .delete()
            .addOnSuccessListener {
                Log.d(common.FBDBTAG, "Document successfully deleted")
                onSuccess("Document successfully deleted")
            }
            .addOnFailureListener { e ->
                Log.w(common.FBDBTAG, "Error deleting document", e)
                onFailure(e.toString())
            }
    }


}