package org.wit.supplyco.models

import timber.log.Timber.i

var supplierId = 0L

internal fun getId(): Long {
    return supplierId ++
}

class SupplierMemRepo : SupplierRepo {

    val suppliers = ArrayList<SupplierModel>()

    override fun findAll(): List<SupplierModel> {
        return suppliers
    }

    override fun create(supplier: SupplierModel) {
        supplier.id = getId()
        suppliers.add(supplier)
        logAll()
    }

    override fun update(supplier: SupplierModel) {
        val foundSupplier = findById(supplier.id)
        if (foundSupplier != null) {
            foundSupplier.name = supplier.name
            foundSupplier.description = supplier.description
            foundSupplier.contact = supplier.contact
            foundSupplier.email = supplier.email
            foundSupplier.address = supplier.address
            logAll()
        }
    }

    override fun delete(supplier: SupplierModel) {
        val foundSupplier = findById(supplier.id)
        if (foundSupplier != null) {
            suppliers.remove(foundSupplier)
        }
        logAll()
    }

    override fun deleteAll() {
        suppliers.clear()
        logAll()
    }

    private fun logAll() {
        suppliers.forEach { i("$it") }
    }

    private fun findById(id: Long): SupplierModel? {
        return suppliers.find { s -> s.id == id }
    }
}