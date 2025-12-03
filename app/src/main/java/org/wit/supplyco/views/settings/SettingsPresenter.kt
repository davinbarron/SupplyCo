package org.wit.supplyco.views.settings

import android.app.Activity
import org.wit.supplyco.main.MainApp
import org.wit.supplyco.models.SupplierRepo

class SettingsPresenter(private val view: SettingsView) {

    private val app: MainApp = view.application as MainApp
    private val repo: SupplierRepo = app.suppliers

    fun doDeleteAllSuppliers() {
        repo.deleteAll()
        view.showMessage("All suppliers deleted successfully!")
        view.closeWithResult(Activity.RESULT_OK)
    }
}