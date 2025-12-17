package org.wit.supplyco.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SupplierModel (
    var id: String? = null,
    var name: String = "",
    var description: String = "",
    var contact: String = "",
    var email: String = "",
    var address: String = "",
    var image: String? = null,
    var lat: Double = 0.0,
    var lng: Double = 0.0,
    var zoom: Float = 12f
) : Parcelable
