package org.wit.supplyco.views.item

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
import org.wit.supplyco.databinding.ActivityItemBinding
import org.wit.supplyco.models.ItemModel
import org.wit.supplyco.models.SupplierModel

class ItemView : AppCompatActivity() {

    private lateinit var binding: ActivityItemBinding
    lateinit var presenter: ItemPresenter
    var item = ItemModel()
    lateinit var supplier: SupplierModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItemBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbarAddItem)

        supplier = intent.getParcelableExtra("supplier")!!

        presenter = ItemPresenter(this)

        binding.buttonAddItem.setOnClickListener {
            presenter.doAddOrSave(
                binding.itemName.text.toString(),
                binding.itemDescription.text.toString(),
                binding.itemAmountPicker.value,
                binding.itemPrice.text.toString().toDoubleOrNull() ?: 0.0
            )
        }

        binding.buttonDeleteItem.setOnClickListener {
            presenter.doDelete()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.choose_image -> presenter.doSelectImage()
            R.id.item_cancel -> presenter.doCancel()
        }
        return super.onOptionsItemSelected(item)
    }

    fun showItem(item: ItemModel) {
        binding.itemName.setText(item.name)
        binding.itemDescription.setText(item.description)
        binding.itemAmountPicker.value = item.amount
        binding.itemPrice.setText(item.price.toString())
        binding.buttonAddItem.text = getString(R.string.button_saveItem)
        binding.itemActivityTitle.text = getString(R.string.toolbar_title_item_details)
        binding.buttonDeleteItem.visibility = View.VISIBLE
        Picasso.get().load(item.image).into(binding.itemImage)
    }

    fun updateImage(image: Uri) {
        Picasso.get().load(image).into(binding.itemImage)
    }

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
