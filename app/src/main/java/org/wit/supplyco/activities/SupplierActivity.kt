package org.wit.supplyco.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
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

                // Log all suppliers
                for (i in app.suppliers.indices) {
                    i("supplier[$i]:${this.app.suppliers[i]}")
                }
            }
            else {
                Snackbar
                    .make(it,"Please Enter a name", Snackbar.LENGTH_LONG)
                    .show()
            }
        }
    }
}