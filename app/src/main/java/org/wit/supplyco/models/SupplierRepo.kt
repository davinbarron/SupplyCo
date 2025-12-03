package org.wit.supplyco.models

interface SupplierRepo {
    fun findAll(callback: (List<SupplierModel>) -> Unit)
    fun findSupplier(query: String, callback: (List<SupplierModel>) -> Unit)
    fun create(supplier: SupplierModel)
    fun update(supplier: SupplierModel)
    fun delete(supplier: SupplierModel)
    fun deleteAll()
    fun listenAll(callback: (List<SupplierModel>) -> Unit)
}