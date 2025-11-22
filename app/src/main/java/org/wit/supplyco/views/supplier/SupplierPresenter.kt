package org.wit.supplyco.views.supplier

import android.content.Intent
import org.wit.supplyco.main.MainApp
import org.wit.supplyco.models.SupplierModel
import org.wit.supplyco.models.SupplierRepo

class SupplierPresenter(private val view: SupplierView) {

    private var supplier = SupplierModel()
    private var edit = false
    private val app: MainApp = view.application as MainApp
    private val repo: SupplierRepo = app.suppliers

    fun initSupplier(intent: Intent) {
        if (intent.hasExtra("supplier_edit")) {
            edit = true
            supplier = intent.extras?.getParcelable("supplier_edit")!!
            view.showSupplier(supplier)
        }
    }

    fun doAddOrSave(name: String, desc: String, contact: String, email: String, address: String) {
        if (name.isNotEmpty() && desc.isNotEmpty() && contact.isNotEmpty() && email.isNotEmpty() && address.isNotEmpty()) {
            supplier.apply {
                this.name = name
                this.description = desc
                this.contact = contact
                this.email = email
                this.address = address
            }

            if (edit) {
                repo.update(supplier)
                view.showMessage("Supplier updated successfully!")
            } else {
                repo.create(supplier)
                view.showMessage("Supplier added successfully!")
            }
            view.setResult(android.app.Activity.RESULT_OK)
            view.finish()
        } else {
            view.showError("Please fill in all fields!")
        }
    }

    fun doDelete() {
        repo.delete(supplier)
        view.showMessage("Supplier deleted successfully!")
        view.setResult(android.app.Activity.RESULT_OK)
        view.finish()
    }

    fun doCancel() {
        view.finish()
    }
}