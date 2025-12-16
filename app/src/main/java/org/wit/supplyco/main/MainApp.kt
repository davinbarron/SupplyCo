package org.wit.supplyco.main

import android.app.Application
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import org.wit.supplyco.models.ItemFirestoreRepo
import org.wit.supplyco.models.ItemRepo
import org.wit.supplyco.models.SupplierFirestoreRepo
import org.wit.supplyco.models.SupplierRepo
import org.wit.supplyco.models.UserRepo
import org.wit.supplyco.models.UserFirebaseRepo
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {

    lateinit var suppliers: SupplierRepo
    lateinit var items: ItemRepo
    lateinit var users: UserRepo
    lateinit var auth: FirebaseAuth

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        FirebaseApp.initializeApp(this)

        suppliers = SupplierFirestoreRepo()
        items = ItemFirestoreRepo()
        auth = FirebaseAuth.getInstance()
        users = UserFirebaseRepo(auth)

        i("SupplyCo started")
    }
}
