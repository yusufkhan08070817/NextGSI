import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.ionexa.nextgsi.SingleTon.NaveLabels
import com.ionexa.nextgsi.SingleTon.Navigation

data class SellerInfo(
    val name: String,
    val description: String,
    val imageUrl: String,
    val email: String,
    val phoneNumber: String,
    val chatOption: String,
    val overallRating: Float,
    val customerReviews: List<String>,
    val returnPolicy: String,
    val warrantyInfo: String,
    val fulfillmentRate: Float,
    val responseTime: String,
    val salesVolume: Int,
    val socialMediaLinks: List<String>,
    var shopid:String="",
)

@Composable
fun SellerProfile(
    sellerInfo: SellerInfo,
    onEdit: (SellerInfo) -> Unit
) {
    var scroll= rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxWidth(1f)
            .fillMaxHeight(0.99f)
            .padding(16.dp)
            .verticalScroll(scroll),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TextButton(onClick = { Firebase.auth.signOut();Navigation.navController.navigate(NaveLabels.Login) }) {
            Text(text = "Logout")

        }
        Image(
            painter = rememberImagePainter(sellerInfo.imageUrl),
            contentDescription = null,
            modifier = Modifier
                .size(120.dp)
                .clip(shape = CircleShape)
        )
        Text(text = sellerInfo.name, style = MaterialTheme.typography.bodyMedium)
        Text(text = sellerInfo.description)

        // Editable fields
        Spacer(modifier = Modifier.height(16.dp))
        EditableField(label = "Email", value = sellerInfo.email) { newValue ->
            onEdit(sellerInfo.copy(email = newValue))
        }
        EditableField(label = "Phone Number", value = sellerInfo.phoneNumber) { newValue ->
            onEdit(sellerInfo.copy(phoneNumber = newValue))
        }
        EditableField(label = "Chat Option", value = sellerInfo.chatOption) { newValue ->
            onEdit(sellerInfo.copy(chatOption = newValue))
        }

        // Overall rating and reviews
        RatingBar(rating = sellerInfo.overallRating)

        sellerInfo.customerReviews.map {
            Text(text = it)
        }

        // Store description
        Text(text = "Store Description: ${sellerInfo.description}")
        Text(text = "Location: Your Store Location")
        Text(text = "Shipping Policies: Fast shipping within the US!")

        // Product catalog
        listOf("Product 1", "Product 2", "Product 3").map {
            Text(text = it)
        }


        // Seller's policies
        Text(text = "Return and Refund Policy: Easy returns within 30 days.")
        Text(text = "Warranty Information: 1-year warranty on all products.")

        // Performance metrics
        Text(text = "Fulfillment Rate: ${sellerInfo.fulfillmentRate}%")
        Text(text = "Response Time: ${sellerInfo.responseTime}")
        Text(text = "Sales Volume: ${sellerInfo.salesVolume} units sold")

        // Social proof and links
        Text(text = "Follow us on social media:")
        sellerInfo.socialMediaLinks.forEach { link ->
            Text(text = link)
        }
    }
}

@Composable
fun EditableField(label: String, value: String, onValueChange: (String) -> Unit) {
    Column {
        Text(text = label, style = MaterialTheme.typography.displaySmall)
        BasicTextField(
            value = value,
            onValueChange = { onValueChange(it) },
            textStyle = TextStyle(color = Color.Black)
        )
    }
}

@Composable
fun RatingBar(rating: Float) {
    Row {
        repeat(5) { index ->
            val starColor = if (index < rating) Color.Yellow else Color.Gray
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = null,
                tint = starColor,
                modifier = Modifier.width(24.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
        }
    }
}
