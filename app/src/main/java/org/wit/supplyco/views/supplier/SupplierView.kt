package org.wit.supplyco.views.supplier

import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import org.wit.supplyco.R
import org.wit.supplyco.databinding.ActivitySupplierBinding
import org.wit.supplyco.models.SupplierModel
import timber.log.Timber.i

class SupplierView : AppCompatActivity() {

    private lateinit var binding: ActivitySupplierBinding
    private lateinit var presenter: SupplierPresenter
    var supplier = SupplierModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySupplierBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbarAdd)

        presenter = SupplierPresenter(this)

        binding.buttonAddSupplier.setOnClickListener {
            presenter.doAddOrSave(
                binding.supplierName.text.toString(),
                binding.supplierDescription.text.toString(),
                binding.supplierContact.text.toString(),
                binding.supplierEmail.text.toString(),
                binding.supplierAddress.text.toString()
            )
        }

        binding.buttonDeleteSupplier.setOnClickListener {
            presenter.doDelete()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_supplier, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.choose_image -> presenter.doSelectImage()
            R.id.item_cancel -> presenter.doCancel()
        }
        return super.onOptionsItemSelected(item)
    }

    fun showSupplier(supplier: SupplierModel) {
        binding.supplierName.setText(supplier.name)
        binding.supplierDescription.setText(supplier.description)
        binding.supplierContact.setText(supplier.contact)
        binding.supplierEmail.setText(supplier.email)
        binding.supplierAddress.setText(supplier.address)
        binding.buttonAddSupplier.text = getString(R.string.button_saveSupplier)
        binding.supplierActivityTitle.text = getString(R.string.toolbar_title_supplier_details)
        binding.buttonDeleteSupplier.visibility = View.VISIBLE
        Picasso.get()
            .load(supplier.image)
            .into(binding.supplierImage)
    }

    fun updateImage(image: Uri){
        i("Image updated")
        Picasso.get()
            .load(image)
            .into(binding.supplierImage)
    }

    fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun showError(error: String) {
        Snackbar.make(binding.root, error, Snackbar.LENGTH_LONG).show()
    }
}