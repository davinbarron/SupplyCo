package org.wit.supplyco.helper

import android.content.Context
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import org.wit.supplyco.R

fun showImagePicker(intentLauncher: ActivityResultLauncher<Intent>, context: Context) {
    val imagePickerTargetIntent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
        addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION)
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        type = "image/*"
    }

    val chooser = Intent.createChooser(
        imagePickerTargetIntent,
        context.getString(R.string.select_image)
    )

    intentLauncher.launch(chooser)
}