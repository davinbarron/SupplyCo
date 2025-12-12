package org.wit.supplyco.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ItemModel(
    var id: String? = null,
    var name: String = "",
    var description: String = "",
    var amount: Int = 0,
    var price: Double = 0.0,
    var releaseDate: Long? = null,
    var image: String? = null
) : Parcelable