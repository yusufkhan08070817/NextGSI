package com.ionexa.nextgsi.Pages

import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.razorpay.Checkout
import org.json.JSONObject

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
    }
}
