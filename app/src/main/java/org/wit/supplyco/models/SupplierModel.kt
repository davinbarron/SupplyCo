package org.wit.supplyco.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SupplierModel (
    var supplierId: Long = 0,
    var name: String = "",
    var description: String = "",
    var contact: String = "",
    var email: String = "",
    var address: String = ""
) : Parcelable
