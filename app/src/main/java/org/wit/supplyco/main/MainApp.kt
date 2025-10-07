package org.wit.supplyco.main

import android.app.Application
import org.wit.supplyco.models.SupplierModel
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {

    val suppliers = ArrayList<SupplierModel>()

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        i("SupplyCo started")
    }
}
