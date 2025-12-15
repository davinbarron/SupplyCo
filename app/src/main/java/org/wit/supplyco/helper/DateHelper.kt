package org.wit.supplyco.helper

import org.wit.supplyco.R
import java.text.SimpleDateFormat
import java.util.*

fun Long?.toFormattedDate(context: android.content.Context): String {
    return this?.let {
        SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(Date(it))
    } ?: context.getString(R.string.item_release_date_not_set)
}
