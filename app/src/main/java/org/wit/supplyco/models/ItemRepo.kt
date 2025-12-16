package org.wit.supplyco.models

interface ItemRepo {
    fun findItemForSupplier(supplierId: String, query: String, callback: (List<ItemModel>) -> Unit)
    fun create(supplierId: String, item: ItemModel)
    fun update(supplierId: String, item: ItemModel)
    fun delete(supplierId: String, item: ItemModel)
    fun deleteAllForSupplier(supplierId: String)
    fun listenAllForSupplier(supplierId: String, callback: (List<ItemModel>) -> Unit)
}
