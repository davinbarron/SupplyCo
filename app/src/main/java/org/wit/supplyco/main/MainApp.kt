package org.wit.supplyco.main

import android.app.Application
import com.google.firebase.FirebaseApp
import org.wit.supplyco.models.SupplierFirestoreRepo
import org.wit.supplyco.models.SupplierRepo
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {

    lateinit var suppliers: SupplierRepo

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        // suppliers = SupplierJSONRepo(this)
        FirebaseApp.initializeApp(this)
        suppliers = SupplierFirestoreRepo()
        i("SupplyCo started")
    }
}
