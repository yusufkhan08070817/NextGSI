package com.ionexa.nextgsi.Pages

import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.ionexa.nextgsi.FBFireBase.FSDB
import com.ionexa.nextgsi.SingleTon.NaveLabels
import com.ionexa.nextgsi.SingleTon.Navigation
import com.ionexa.nextgsi.SingleTon.common
import com.razorpay.Checkout
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.json.JSONObject
import kotlin.coroutines.coroutineContext

@Composable
fun RazorpayPaymentButton(activity: Activity) {
    // Button to trigger payment
    Button(
        onClick = { initiatePayment(activity) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(text = "Pay Now")
    }
}

private fun initiatePayment(activity: Activity) {


    val checkout = Checkout()

    // Use the test Key ID
    checkout.setKeyID("rzp_test_1DP5mmOlF5dU4z")

    try {
        val options = JSONObject().apply {
            put("name", "Razorpay Corp")
            put("description", "Demoing Charges")
            put("currency", "INR")
            put("amount", "10000") // 100 INR in paise

            // Optional settings
            put("send_sms_hash", true)
            put("allow_rotation", true)
            put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png")

            // Prefill user data
            val prefill = JSONObject().apply {
                put("email", "test@razorpay.com")
                put("contact", "9876543210")
            }
            put("prefill", prefill)
        }

        checkout.open(activity, options)

    } catch (e: Exception) {
        Toast.makeText(activity, "Error in payment: ${e.message}", Toast.LENGTH_LONG).show()
        e.printStackTrace()
    }
}

@Composable
fun PaymentScreen(activity: Activity) {
    var corutineScope= rememberCoroutineScope()
    var fsdb=FSDB()
    var dd by remember {
        mutableStateOf(false)
    }
    var context= LocalContext.current
    var listdata = remember { mutableStateListOf<Map<String, Any>>() }
    LaunchedEffect(key1=true) { fsdb.getDataFromFireStoreDB("users", common.myid.value, onSuccess = {

        if (it!= null)
        {
            var data=it["cart"] as List<Map<String,Any>>
            Log.e("data",data.toString())
            listdata.clear()
            listdata.addAll(data)
        }
    }){

    }

    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Razorpay Payment", style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(20.dp))
        RazorpayPaymentButton(activity = activity)
        Spacer(modifier = Modifier.height(40.dp))
        Text(text = "or")
        TextButton(onClick = {

            corutineScope.launch {
                fsdb.updateDataInFirestore("users",common.myid.value,"history",listdata, onsuccess = {

dd=true


                }, onfailure = {
                    Toast.makeText(activity,it,Toast.LENGTH_LONG).show()
                })
                while (!dd)
                    delay(100)
                    if (dd)
                    {
                        fsdb.updateDataInFirestore("users",common.myid.value,"history",listdata, onsuccess = {}){}
                        Toast.makeText(context, "clean cart", Toast.LENGTH_SHORT).show()
                        var li= mutableListOf<Map<String,Any>>()
                        fsdb.updateDataInFirestore("users",common.myid.value,"cart",li, onsuccess = {

                            Navigation.navController.navigate(NaveLabels.Home)
dd=true

                        }, onfailure = {
                            Toast.makeText(activity,it,Toast.LENGTH_LONG).show()
                        })
                    }

            }

           }) {
          Text(text = "cash on delivery")
        }
    }
}
