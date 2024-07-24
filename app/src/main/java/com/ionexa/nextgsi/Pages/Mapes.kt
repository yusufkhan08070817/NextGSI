package com.ionexa.nextgsi.Components

import android.content.pm.PackageManager
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.ionexa.nextgsi.Classes.JSInterface


@Composable
fun MapeKC(modifier: Modifier,
            onLocationPicked: (Double, Double) -> Unit,
            onCurrentLocation: (Double, Double) -> Unit,
           onMapeClicked: () -> Unit,
            onWebViewReady: (WebView) -> Unit,

) {

    AndroidView(
        factory = { context ->
            WebView(context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                settings.javaScriptEnabled = true
                webViewClient = WebViewClient()
                addJavascriptInterface(JSInterface(onLocationPicked, onCurrentLocation), "Android")
                loadUrl("file:///android_asset/leaflet_map.html")
                onWebViewReady(this)
            }
        },
        modifier =modifier.fillMaxWidth().fillMaxHeight(0.8f).clickable { onMapeClicked() }
    )
}
