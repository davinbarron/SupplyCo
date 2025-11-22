package org.wit.supplyco.views.supplierlist

import org.wit.supplyco.main.MainApp
import org.wit.supplyco.models.SupplierModel
import org.wit.supplyco.models.SupplierRepo

class SupplierListPresenter(private val view: SupplierListView) {

    private val app: MainApp = view.application as MainApp
    private val repo: SupplierRepo = app.suppliers

    fun loadSuppliers(query: String = "") {
        val suppliers = if (query.isBlank()) repo.findAll() else repo.findSupplier(query)
        view.showSuppliers(suppliers)
    }

    fun doEditSupplier(supplier: SupplierModel) {
        view.navigateToSupplier(supplier)
    }

    fun doAddSupplier() {
        view.navigateToAddSupplier()
    }

    fun doOpenSettings() {
        view.navigateToSettings()
    }
}