package org.wit.supplyco.views.itemlist

import android.content.Intent
import org.wit.supplyco.main.MainApp
import org.wit.supplyco.models.ItemModel
import org.wit.supplyco.models.SupplierModel
import org.wit.supplyco.views.item.ItemView
import org.wit.supplyco.views.supplier.SupplierView

class ItemListPresenter(val view: ItemListView, private val supplier: SupplierModel) {

    private val app: MainApp = view.application as MainApp
    private val repo = app.items

    fun startListening() {
        // Listen for items belonging to this supplier
        repo.listenAllForSupplier(supplier.id!!) { items ->
            view.showItems(items)
        }
    }

    fun doAddItem() {
        val intent = Intent(view, ItemView::class.java)
        intent.putExtra("supplier", supplier)
        view.startActivity(intent)
    }

    fun doEditItem(item: ItemModel) {
        val intent = Intent(view, ItemView::class.java)
        intent.putExtra("item_edit", item)
        intent.putExtra("supplier", supplier)
        view.startActivity(intent)
    }

    fun doEditSupplier() {
        val intent = Intent(view, SupplierView::class.java)
        intent.putExtra("supplier_edit", supplier)
        view.startActivity(intent)
    }
}
