package org.wit.supplyco.main

import android.app.Application
import org.wit.supplyco.models.SupplierJSONRepo
import org.wit.supplyco.models.SupplierRepo
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {

    lateinit var suppliers: SupplierRepo

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        suppliers = SupplierJSONRepo(this)
        i("SupplyCo started")
    }
}
