package org.wit.supplyco.models

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SupplierModel (
    var id: Long = 0,
    var name: String = "",
    var description: String = "",
    var contact: String = "",
    var email: String = "",
    var address: String = "",
    var image: Uri = Uri.EMPTY
) : Parcelable
