package org.wit.supplyco.views.supplierlist

import android.app.Activity
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import org.wit.supplyco.main.MainApp
import org.wit.supplyco.models.SupplierModel
import org.wit.supplyco.views.settings.SettingsView
import org.wit.supplyco.views.supplier.SupplierView

class SupplierListPresenter(val view: SupplierListView) {

    private val app: MainApp = view.application as MainApp
    private val repo = app.suppliers

    private lateinit var refreshIntentLauncher: ActivityResultLauncher<Intent>
    private lateinit var settingsIntentLauncher: ActivityResultLauncher<Intent>
    private var position: Int = 0

    init {
        registerRefreshCallback()
        registerSettingsCallback()
    }

    fun startListening() {
        repo.listenAll { suppliers ->
            view.showSuppliers(suppliers)
        }
    }

    fun loadSuppliers(query: String = "") {
        if (query.isBlank()) {
            startListening()
        } else {
            repo.findSupplier(query) { suppliers -> view.showSuppliers(suppliers) }
        }
    }

    fun doAddSupplier() {
        val launcherIntent = Intent(view, SupplierView::class.java)
        refreshIntentLauncher.launch(launcherIntent)
    }

    fun doEditSupplier(supplier: SupplierModel, pos: Int) {
        val launcherIntent = Intent(view, SupplierView::class.java)
        launcherIntent.putExtra("supplier_edit", supplier)
        position = pos
        refreshIntentLauncher.launch(launcherIntent)
    }

    fun doOpenSettings() {
        val launcherIntent = Intent(view, SettingsView::class.java)
        settingsIntentLauncher.launch(launcherIntent)
    }

    private fun registerRefreshCallback() {
        refreshIntentLauncher =
            view.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == Activity.RESULT_OK) {
                    loadSuppliers()
                } else if (it.resultCode == 99) {
                    view.onDelete(position)
                }
            }
    }

    private fun registerSettingsCallback() {
        settingsIntentLauncher =
            view.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { }
    }
}