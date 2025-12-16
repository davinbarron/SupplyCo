package org.wit.supplyco.main

import android.app.Application
import com.google.firebase.FirebaseApp
import org.wit.supplyco.models.ItemFirestoreRepo
import org.wit.supplyco.models.ItemRepo
import org.wit.supplyco.models.SupplierFirestoreRepo
import org.wit.supplyco.models.SupplierRepo
import org.wit.supplyco.models.UserRepo
import org.wit.supplyco.models.UserStoreRepo
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {

    lateinit var suppliers: SupplierRepo
    lateinit var items: ItemRepo
    lateinit var users: UserRepo

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        FirebaseApp.initializeApp(this)

        suppliers = SupplierFirestoreRepo()
        items = ItemFirestoreRepo()
        users = UserStoreRepo()

        i("SupplyCo started")
    }
}
