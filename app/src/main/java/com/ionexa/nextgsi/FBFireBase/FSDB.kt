package com.ionexa.nextgsi.FBFireBase

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken

import com.ionexa.nextgsi.SingleTon.common
import com.ionexa.nextgsi.SingleTon.common.db
import kotlinx.coroutines.tasks.await
import kotlin.reflect.KClass
import kotlin.reflect.full.createType
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

    suspend fun updateDataInFirestore(
        collectionName: String,
        documentName: String,
        fieldName: String, // The field you want to update
        data: Any, // The data to set or append
        appendToArray: Boolean = false, // Flag to append if it's an array
        onsuccess: (log: String) -> Unit,
        onfailure: (log: String) -> Unit
    ) {
        try {
            val documentRef = db.collection(collectionName).document(documentName)

            // Get the document snapshot to check if it exists
            val documentSnapshot = documentRef.get().await()

            if (documentSnapshot.exists()) {
                if (appendToArray) {
                    // Append data to array field if it exists
                    val existingArray = documentSnapshot.get(fieldName) as? MutableList<Any>

                    if (existingArray != null) {
                        // Add new data to the existing array
                        existingArray.add(data)

                        // Update the document with the new array
                        documentRef.update(fieldName, existingArray).await()
                    } else {
                        // If the array doesn't exist, create a new one with the data
                        documentRef.update(fieldName, listOf(data)).await()
                    }
                } else {
                    // For non-array fields, just update the field with the new data
                    documentRef.update(fieldName, data).await()
                }
            } else {
                // If the document doesn't exist, create it with the provided data
                if (appendToArray) {
                    // Create a document with an array field
                    documentRef.set(hashMapOf(fieldName to listOf(data))).await()
                } else {
                    // Create a document with the provided field and data
                    documentRef.set(hashMapOf(fieldName to data)).await()
                }
            }

            onsuccess("Data updated successfully")
        } catch (e: Exception) {
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
    fun parseJsonToMap(jsonString: String): HashMap<String, Any>? {
        return try {
            val gson = Gson()
            val typeToken = object : TypeToken<HashMap<String, Any>>() {}.type
            gson.fromJson<HashMap<String, Any>>(jsonString, typeToken)
        } catch (e: Exception) {
          Log.e("Json parse error","@:$jsonString")
            e.printStackTrace()
            null
        }
    }
    fun convertMapToJson(map: Map<String, Any>): String {
        val gson = Gson()
        return gson.toJson(map)
    }
    fun parseJsonString(jsonString: String): JsonObject {
        return try {
            val jsonElement: JsonElement = JsonParser.parseString(jsonString)
            if (jsonElement.isJsonObject) {
                jsonElement.asJsonObject
            } else {
                throw IllegalArgumentException("The provided JSON string does not represent a JSON object.")
            }
        } catch (e: Exception) {
            throw IllegalArgumentException("Invalid JSON string provided.", e)
        }
    }
    fun extractKeysFromJsonObject(jsonObject: JsonObject): MutableList<String> {
        val keyList = mutableListOf<String>()
        val keys = jsonObject.keySet()

        for (key in keys) {
            keyList.add(key)
        }

        return keyList
    }
    fun extractJsonStringsFromJsonObject(jsonObject: JsonObject): MutableList<JsonObject> {
        val jsonList = mutableListOf<JsonObject>()
        val gson = Gson()

        for ((_, value) in jsonObject.entrySet()) {
            if (value.isJsonObject) {
                // Convert JsonObject to a string and add it to the list
                jsonList.add(parseJsonString(gson.toJson(value.asJsonObject)))
            }
        }

        return jsonList
    }
    fun extractValuesFromJsonObject(jsonObject: JsonObject): MutableList<JsonObject> {
        var raw:MutableList<JsonObject> = mutableListOf()
        val arraykey=extractKeysFromJsonObject(jsonObject)
        arraykey.map { value->
            if(jsonObject.get(value).isJsonObject)
            {
                raw.add(jsonObject.get(value).asJsonObject)
            }
        }
        return raw
    }

    fun extractElementValuesFromJsonObject(jsonElement: JsonElement): MutableList<String> {
        val raw: MutableList<String> = mutableListOf()

        // Check if jsonElement is indeed a JsonObject
        if (jsonElement.isJsonObject) {
            val jsonObject = jsonElement.asJsonObject
            val arrayKey = jsonObject.keySet().toMutableList()

            // Iterate over the keys and extract the string values if they exist
            arrayKey.map { key ->
                if (jsonObject.get(key).isJsonPrimitive) {
                    raw.add(jsonObject.get(key).asString)
                }
            }
        }

        return raw
    }
    fun uploadListToFirestore(
        collectionPath: String,
        documentId: String,
        dataList: List<Any>,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val firestore = FirebaseFirestore.getInstance()
        val dataMap = mapOf("items" to dataList)

        firestore.collection(collectionPath)
            .document(documentId)
            .set(dataMap, SetOptions.merge())
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }



    fun <T : Any> hashMapToNestedDataClass(map: Map<String, Any>, dataClass: KClass<T>): T? {
        val constructor = dataClass.primaryConstructor ?: return null
        val args = constructor.parameters.associateWith { param ->
            val value = map[param.name]
            val paramType = param.type

            when {
                // If parameter is another data class
                paramType.classifier is KClass<*> && (paramType.classifier as KClass<*>).isData ->
                    hashMapToNestedDataClass(value as Map<String, Any>, paramType.classifier as KClass<*>)

                // If parameter is a List
                paramType.classifier == List::class -> {
                    val elementType = paramType.arguments[0].type?.classifier as? KClass<*>
                    (value as? List<*>)?.map { element ->
                        if (elementType?.isData == true && element is Map<*, *>) {
                            hashMapToNestedDataClass(element as Map<String, Any>, elementType)
                        } else {
                            element
                        }
                    }
                }

                // Handle type mismatches (String to Int, String to Float, etc.)
                paramType.classifier == Int::class -> (value as? String)?.toIntOrNull() ?: value as? Int
                paramType.classifier == Float::class -> (value as? String)?.toFloatOrNull() ?: value as? Float
                paramType.classifier == Boolean::class -> (value as? String)?.toBoolean() ?: value as? Boolean

                // For primitive types, just use the value directly
                else -> value
            }
        }
        return constructor.callBy(args)
    }
}
