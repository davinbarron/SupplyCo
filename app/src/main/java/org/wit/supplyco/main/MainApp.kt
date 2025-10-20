package org.wit.supplyco.main

import android.app.Application
import org.wit.supplyco.models.SupplierMemRepo
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {

    val suppliers = SupplierMemRepo()

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        i("SupplyCo started")
    }
}
