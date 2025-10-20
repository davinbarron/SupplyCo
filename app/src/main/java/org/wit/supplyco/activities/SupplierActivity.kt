package org.wit.supplyco.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import org.wit.supplyco.R
import org.wit.supplyco.databinding.ActivitySupplierBinding
import org.wit.supplyco.main.MainApp
import org.wit.supplyco.models.SupplierModel
import timber.log.Timber.i

class SupplierActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySupplierBinding
    lateinit var app: MainApp

    var supplier = SupplierModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySupplierBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)

        app = application as MainApp
        i("Supplier Activity started..")

        if (intent.hasExtra("supplier_edit")) {
            supplier = intent.extras?.getParcelable("supplier_edit")!!
            binding.buttonAddSupplier.text = getString(R.string.button_saveSupplier)
            binding.supplierActivityTitle.text = getString(R.string.toolbar_title_supplier_details)

            // Populating the form with the selected suppliers details when the intention is to edit a supplier
            binding.supplierName.setText(supplier.name)
            binding.supplierDescription.setText(supplier.description)
            binding.supplierContact.setText(supplier.contact)
            binding.supplierEmail.setText(supplier.email)
            binding.supplierAddress.setText(supplier.address)
        } else {
            binding.buttonAddSupplier.text = getString(R.string.button_addSupplier)
            binding.supplierActivityTitle.text = getString(R.string.toolbar_title_add_supplier)
        }

        binding.buttonAddSupplier.setOnClickListener() {

            // Binding the supplier data model to UI elements
            supplier.apply {
                name = binding.supplierName.text.toString()
                description = binding.supplierDescription.text.toString()
                contact = binding.supplierContact.text.toString()
                email = binding.supplierEmail.text.toString()
                address = binding.supplierAddress.text.toString()
            }

            if (isValidSupplier()) {

                if (intent.hasExtra("supplier_edit")) {
                    app.suppliers.update(supplier.copy())

                    Toast.makeText(this, getString(R.string.supplier_updated), Toast.LENGTH_SHORT)
                        .show()
                } else {
                    app.suppliers.create(supplier.copy())

                    Toast.makeText(this, getString(R.string.supplier_added), Toast.LENGTH_SHORT)
                        .show()
                }

                setResult(RESULT_OK)
                finish()

                // Clear the text fields
                binding.supplierName.text.clear()
                binding.supplierDescription.text.clear()
                binding.supplierContact.text.clear()
                binding.supplierEmail.text.clear()
                binding.supplierAddress.text.clear()
            }
            else {
                Snackbar
                    .make(it,getString(R.string.supplier_field_warning), Snackbar.LENGTH_LONG)
                    .show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_supplier, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_cancel -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun isValidSupplier(): Boolean {
        return supplier.name.isNotEmpty() &&
                supplier.description.isNotEmpty() &&
                supplier.contact.isNotEmpty() &&
                supplier.email.isNotEmpty() &&
                supplier.address.isNotEmpty()
    }

}