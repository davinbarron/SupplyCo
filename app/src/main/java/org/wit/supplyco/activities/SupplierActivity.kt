package org.wit.supplyco.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.wit.supplyco.R
import timber.log.Timber

class SupplierActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Timber.plant(Timber.DebugTree())
        Timber.i("Main Activity started..")
    }
}