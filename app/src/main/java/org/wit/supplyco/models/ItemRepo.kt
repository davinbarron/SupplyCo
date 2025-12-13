package org.wit.supplyco.models

interface ItemRepo {
    fun findAllForSupplier(supplierId: String, callback: (List<ItemModel>) -> Unit)
    fun create(supplierId: String, item: ItemModel)
    fun update(supplierId: String, item: ItemModel)
    fun delete(supplierId: String, item: ItemModel)
}
