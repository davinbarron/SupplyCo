package org.wit.supplyco.views.settings

import android.os.Bundle
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import org.wit.supplyco.R
import org.wit.supplyco.databinding.ActivitySettingsBinding
import org.wit.supplyco.views.base.BaseDrawerActivity

class SettingsView : BaseDrawerActivity() {

    private lateinit var binding: ActivitySettingsBinding
    lateinit var presenter: SettingsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setupDrawer(binding.root, binding.toolbarSettings)

        presenter = SettingsPresenter(this)

        // Decide mode based on intent
        val mode = intent.getStringExtra("settings_mode") ?: "suppliers"
        val supplierId = intent.getStringExtra("supplier_id")

        if (mode == "items" && supplierId != null) {
            binding.buttonDeleteAll.text = getString(R.string.delete_allItems)

            binding.buttonDeleteAll.setOnClickListener {
                presenter.doDeleteAllItems(supplierId)
            }
        } else {
            binding.buttonDeleteAll.text = getString(R.string.delete_allSuppliers)

            binding.buttonDeleteAll.setOnClickListener {
                presenter.doDeleteAllSuppliers()
            }
        }
    }

    // Presenter callbacks
    fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun showError(error: String) {
        Snackbar.make(binding.root, error, Snackbar.LENGTH_LONG).show()
    }

    fun closeWithResult(resultCode: Int) {
        setResult(resultCode)
        finish()
    }
}