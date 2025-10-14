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

        binding.buttonAddSupplier.setOnClickListener() {

            // Binding the supplier data model to UI elements
            supplier.name = binding.supplierName.text.toString()
            supplier.description = binding.supplierDescription.text.toString()
            supplier.contact = binding.supplierContact.text.toString()
            supplier.email = binding.supplierEmail.text.toString()
            supplier.address = binding.supplierAddress.text.toString()

            // Some simple validation
            if (supplier.name.isNotEmpty()
                && supplier.description.isNotEmpty()
                && supplier.contact.isNotEmpty()
                && supplier.email.isNotEmpty()
                && supplier.address.isNotEmpty()) {

                // Add copy to the ArrayList and log
                app.suppliers.add(supplier.copy())
                i("Add Button Pressed: $supplier'")

                // User Feedback using Toast
                Toast
                    .makeText(this, "Supplier added successfully!", Toast.LENGTH_SHORT)
                    .show()

                // Log all suppliers
                for (i in app.suppliers.indices) {
                    i("supplier[$i]:${this.app.suppliers[i]}")
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
                    .make(it,"Please fill in all fields", Snackbar.LENGTH_LONG)
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

}