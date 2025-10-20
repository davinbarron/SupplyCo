package org.wit.supplyco.adapters

import org.wit.supplyco.models.SupplierModel

interface SupplierListener {
    fun onSupplierClick(supplier: SupplierModel)
}