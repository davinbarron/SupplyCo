package org.wit.supplyco.views.item

import android.app.DatePickerDialog
import android.net.Uri
import android.os.Bundle
import android.transition.TransitionInflater
import android.transition.TransitionManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import org.wit.supplyco.R
import org.wit.supplyco.databinding.ActivityItemBinding
import org.wit.supplyco.helper.toFormattedDate
import org.wit.supplyco.models.ItemModel
import org.wit.supplyco.models.SupplierModel
import java.util.Calendar

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
                binding.itemPrice.text.toString().toDoubleOrNull() ?: 0.0,
                item.releaseDate
            )
        }

        binding.buttonDeleteItem.setOnClickListener {
            presenter.doDelete()
        }

        // Binding number picker
        binding.itemAmountPicker.minValue = 0
        binding.itemAmountPicker.maxValue = 100
        binding.itemAmountPicker.wrapSelectorWheel = true

        // Binding the calender
        // https://medium.com/@abhisheksuman413/how-to-implement-datepickerdialog-in-android-using-kotlin-45c413e47464
        binding.itemReleaseDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            DatePickerDialog(
                this,
                { _, selectedYear, selectedMonth, selectedDay ->

                    // Get timestamp in millis
                    val pickedMillis = Calendar.getInstance().apply {
                        set(selectedYear, selectedMonth, selectedDay)
                    }.timeInMillis

                    item.releaseDate = pickedMillis
                    binding.itemReleaseDate.text = pickedMillis.toFormattedDate(this)
                },
                year, month, day
            ).show()
        }

        if (savedInstanceState == null) animate()

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
        binding.itemReleaseDate.text = item.releaseDate.toFormattedDate(this)
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

    fun animate() {
        binding.root.post {
            val parent = binding.itemScrollView.parent as ViewGroup
            val transition = TransitionInflater.from(this).inflateTransition(R.transition.scene_enter)

            transition.addTarget(binding.toolbarAddItem)
            transition.addTarget(binding.itemScrollView)

            TransitionManager.beginDelayedTransition(parent, transition)

            binding.toolbarAddItem.visibility = View.VISIBLE
            binding.itemScrollView.visibility = View.VISIBLE
        }
    }
}
