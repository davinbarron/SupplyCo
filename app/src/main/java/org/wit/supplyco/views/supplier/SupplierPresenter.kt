package org.wit.supplyco.views.supplier

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import org.wit.supplyco.main.MainApp
import org.wit.supplyco.models.Location
import org.wit.supplyco.models.SupplierModel
import org.wit.supplyco.models.SupplierRepo
import org.wit.supplyco.views.editlocation.EditLocationView
import timber.log.Timber.i

class SupplierPresenter(private val view: SupplierView) {

    private var supplier = SupplierModel()
    private var edit = false
    private val app: MainApp = view.application as MainApp
    private lateinit var mapIntentLauncher: ActivityResultLauncher<Intent>
    private lateinit var imageIntentLauncher: ActivityResultLauncher<PickVisualMediaRequest>

    init {
        if (view.intent.hasExtra("supplier_edit")) {
            edit = true
            supplier = view.intent.extras?.getParcelable("supplier_edit")!!
            view.showSupplier(supplier)
            view.showLocation(supplier)
        }
        registerImagePickerCallback()
        registerMapCallback()
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
                app.suppliers.update(supplier)
                view.showMessage("Supplier updated successfully!")
            } else {
                app.suppliers.create(supplier)
                view.showMessage("Supplier added successfully!")
            }
            view.closeWithResult(Activity.RESULT_OK)
        } else {
            view.showError("Please fill in all fields!")
        }
    }

    fun doDelete() {
        app.suppliers.delete(supplier)
        view.showMessage("Supplier deleted successfully!")
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

    fun doSetLocation() {
        val location = Location(52.245696, -7.139102, 15f)
        if (supplier.zoom != 0f) {
            location.lat = supplier.lat
            location.lng = supplier.lng
            location.zoom = supplier.zoom
        }
        val launcherIntent = Intent(view, EditLocationView::class.java)
            .putExtra("location", location)
        mapIntentLauncher.launch(launcherIntent)
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
                    // Store as String in Firestore model
                    supplier.image = uri.toString()
                    i("IMG :: ${supplier.image}")
                    view.updateImage(uri)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun registerMapCallback() {
        mapIntentLauncher =
            view.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == AppCompatActivity.RESULT_OK && result.data != null) {
                    val location = result.data!!.extras?.getParcelable<Location>("location")!!
                    i("Location == $location")
                    supplier.lat = location.lat
                    supplier.lng = location.lng
                    supplier.zoom = location.zoom

                    app.suppliers.update(supplier)

                    view.showLocation(supplier)
                }
            }
    }
}