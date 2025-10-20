package org.wit.supplyco.models

interface SupplierRepo {
    fun findAll(): List<SupplierModel>
    fun create(supplier: SupplierModel)
    fun update(supplier: SupplierModel)
    fun deleteAll()
}