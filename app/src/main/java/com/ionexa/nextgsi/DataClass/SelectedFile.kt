package com.ionexa.nextgsi.DataClass

import android.net.Uri

data class SelectedFile(
    val uri: Uri,
    val mimeType: String?,
    val name: String?
)