package org.wit.supplyco.views.settings

import android.app.Activity
import androidx.appcompat.app.AppCompatDelegate
import org.wit.supplyco.main.MainApp
import org.wit.supplyco.models.SupplierRepo
import androidx.core.content.edit

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

    fun doToggleNightMode(isDark: Boolean) {
        val mode = if (isDark) AppCompatDelegate.MODE_NIGHT_YES
        else AppCompatDelegate.MODE_NIGHT_NO
        AppCompatDelegate.setDefaultNightMode(mode)

        val message = if (isDark) "Dark mode enabled" else "Light mode enabled"
        view.showMessage(message)
    }
}