package org.wit.supplyco.views.supplier

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import org.wit.supplyco.main.MainApp
import org.wit.supplyco.models.SupplierModel
import org.wit.supplyco.models.SupplierRepo
import timber.log.Timber.i

class SupplierPresenter(private val view: SupplierView) {

    private var supplier = SupplierModel()
    private var edit = false
    var app: MainApp = view.application as MainApp
    private lateinit var imageIntentLauncher : ActivityResultLauncher<PickVisualMediaRequest>
    private val repo: SupplierRepo = app.suppliers

    init {
        if (view.intent.hasExtra("supplier_edit")) {
            edit = true
            supplier = view.intent.extras?.getParcelable("supplier_edit")!!
            view.showSupplier(supplier)
        }
        registerImagePickerCallback()
    }

    fun doAddOrSave(name: String, desc: String, contact: String, email: String, address: String) {
        if (name.isNotEmpty() && desc.isNotEmpty() && contact.isNotEmpty() && email.isNotEmpty() && address.isNotEmpty()) {
            supplier.apply {
                this.name = name
                this.description = desc
                this.contact = contact
                this.email = email
                this.address = address
            }

            if (edit) {
                repo.update(supplier)
                view.showMessage("Supplier updated successfully!")
            } else {
                repo.create(supplier)
                view.showMessage("Supplier added successfully!")
            }
            view.setResult(android.app.Activity.RESULT_OK)
            view.finish()
        } else {
            view.showError("Please fill in all fields!")
        }
    }

    fun doDelete() {
        repo.delete(supplier)
        view.showMessage("Supplier deleted successfully!")
        view.setResult(android.app.Activity.RESULT_OK)
        view.finish()
    }

    fun doCancel() {
        view.finish()
    }

    fun doSelectImage() {
        val request = PickVisualMediaRequest.Builder()
            .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly)
            .build()
        imageIntentLauncher.launch(request)
    }

    fun cacheSupplier (name: String, desc: String, contact: String, email: String, address: String) {
        supplier.name = name
        supplier.description = desc
        supplier.contact = contact
        supplier.email = email
        supplier.address = address
    }

    private fun registerImagePickerCallback() {
        imageIntentLauncher = view.registerForActivityResult(
            ActivityResultContracts.PickVisualMedia()
        ) {
            try{
                view.contentResolver
                    .takePersistableUriPermission(it!!,
                        Intent.FLAG_GRANT_READ_URI_PERMISSION )
                supplier.image = it // The returned Uri
                i("IMG :: ${supplier.image}")
                view.updateImage(supplier.image)
            }
            catch(e:Exception){
                e.printStackTrace()
            }
        }
    }
}