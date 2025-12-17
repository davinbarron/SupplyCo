package org.wit.supplyco.views.settings

import android.app.Activity
import androidx.appcompat.app.AppCompatDelegate
import org.wit.supplyco.main.MainApp
import org.wit.supplyco.models.SupplierRepo

class SettingsPresenter(private val view: SettingsView) {

    private val app: MainApp = view.application as MainApp
    private val repo: SupplierRepo = app.suppliers
    private val itemRepo = app.items

    fun doDeleteAllSuppliers() {
        repo.deleteAll()
        view.showMessage("All suppliers deleted successfully!")
        view.closeWithResult(Activity.RESULT_OK)
    }

    fun doDeleteAllItems(supplierId: String) {
        itemRepo.deleteAllForSupplier(supplierId)
        view.showMessage("All items deleted successfully for supplier $supplierId!")
        view.closeWithResult(Activity.RESULT_OK)
    }

    fun doToggleNightMode(enabled: Boolean) {
        if (enabled) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            view.showMessage("Night mode enabled")
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            view.showMessage("Night mode disabled")
        }
    }
}