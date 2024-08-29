package com.ionexa.nextgsi.DataClass

import android.net.Uri

data class SelectedFile(
    var uri: Uri,
    var mimeType: String?,
    var name: String?
)