package org.wit.supplyco.views.item

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import org.wit.supplyco.main.MainApp
import org.wit.supplyco.models.ItemModel
import org.wit.supplyco.models.ItemRepo
import timber.log.Timber.i

class ItemPresenter(private val view: ItemView) {

    private var item = ItemModel()
    private var edit = false
    private val app: MainApp = view.application as MainApp
    private val repo: ItemRepo = app.items
    private lateinit var imageIntentLauncher: ActivityResultLauncher<PickVisualMediaRequest>

    init {
        if (view.intent.hasExtra("item_edit")) {
            edit = true
            item = view.intent.extras?.getParcelable("item_edit")!!
            view.showItem(item)
        }
        registerImagePickerCallback()
    }

    fun doAddOrSave(name: String, desc: String, amount: Int, price: Double) {
        if (name.isNotEmpty() && desc.isNotEmpty()) {
            item.apply {
                this.name = name
                this.description = desc
                this.amount = amount
                this.price = price
            }

            if (edit) {
                repo.update(view.supplier.id!!, item)
                view.showMessage("Item updated successfully!")
            } else {
                repo.create(view.supplier.id!!, item)
                view.showMessage("Item added successfully!")
            }
            view.closeWithResult(Activity.RESULT_OK)
        } else {
            view.showError("Please fill in all required fields!")
        }
    }

    fun doDelete() {
        repo.delete(view.supplier.id!!, item)
        view.showMessage("Item deleted successfully!")
        view.closeWithResult(Activity.RESULT_OK)
    }

    fun doCancel() {
        view.closeWithResult(Activity.RESULT_CANCELED)
    }

    fun doSelectImage() {
        val request = PickVisualMediaRequest.Builder()
            .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly)
            .build()
        imageIntentLauncher.launch(request)
    }

    private fun registerImagePickerCallback() {
        imageIntentLauncher = view.registerForActivityResult(
            ActivityResultContracts.PickVisualMedia()
        ) { uri: Uri? ->
            try {
                if (uri != null) {
                    view.contentResolver.takePersistableUriPermission(
                        uri,
                        Intent.FLAG_GRANT_READ_URI_PERMISSION
                    )
                    item.image = uri.toString()
                    i("IMG :: ${item.image}")
                    view.updateImage(uri)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
