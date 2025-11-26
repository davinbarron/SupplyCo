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
            foundSupplier.image = supplier.image
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

    /*
    Making use of the filter method since we want to find all suppliers with the query
    Reason is that find will only return the first element that matches
    It makes sense for id searching since the id will always be unique
    However since the names, descriptions etc may not be unique that means find will not do
    Resource: https://dev.to/khush/find-vs-filter-2528
 */
    override fun findSupplier(query: String): List<SupplierModel> {
        return suppliers.filter { s -> s.name.contains(query)
                || s.description.contains(query)
                || s.contact.contains(query)
                || s.email.contains(query)
                || s.address.contains(query)
        }
    }
}